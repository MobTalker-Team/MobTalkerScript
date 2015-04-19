/*
 * Copyright (C) 2013-2015 Chimaine
 * 
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package net.mobtalker.mobtalkerscript.v3.compiler;

import java.util.ArrayList;

import org.antlr.v4.runtime.ParserRuleContext;

import com.google.common.collect.Lists;

public class TreePattern implements ITreePattern
{
    private final ArrayList<Class<? extends ParserRuleContext>> _pattern;
    
    // ========================================
    
    @SafeVarargs
    public TreePattern( Class<? extends ParserRuleContext>... pattern )
    {
        _pattern = Lists.newArrayList( pattern );
        _pattern.trimToSize();
    }
    
    // ========================================
    
    @Override
    public boolean matches( ParserRuleContext ctx )
    {
        ParserRuleContext cur = ctx.getParent();
        
        for ( Class<? extends ParserRuleContext> element : _pattern )
        {
            if ( ( cur == null ) || ( !element.isAssignableFrom( cur.getClass() ) ) )
            {
                // printFailureMsg( ctx );
                return false;
            }
            
            cur = cur.getParent();
        }
        
        return true;
    }
    
    @Override
    @SuppressWarnings( "unchecked" )
    public <T extends ParserRuleContext> T get( ParserRuleContext ctx )
    {
        ParserRuleContext result = ctx.getParent();
        for ( int i = 1; i < _pattern.size(); i++ )
        {
            result = ctx.getParent();
        }
        
        return (T) result;
    }
    
    // ========================================
    
    protected void printFailureMsg( ParserRuleContext ctx )
    {
        StringBuilder s = new StringBuilder( "Pattern [" );
        
        for ( int i = 0; i < _pattern.size(); i++ )
        {
            Class<? extends ParserRuleContext> clazz = _pattern.get( i );
            s.append( clazz.getSimpleName() );
            s.append( ( i < ( _pattern.size() - 1 ) ) ? ", " : "] did not match [" );
        }
        
        ParserRuleContext cur = ctx;
        for ( /* int i = 0 */;; /* i++ */)
        {
            s.append( cur.getClass().getSimpleName() );
            cur = cur.getParent();
            
            if ( ( cur != null ) /* && ( i < ( _pattern.size() - 1 ) ) */)
            {
                s.append( ", " );
            }
            else
            {
                s.append( "]" );
                break;
            }
        }
        
        System.out.println( s.toString() );
    }
    
}
