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

import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;

import com.google.common.collect.Lists;

public class TreeMultiPattern implements ITreePattern
{
    private final List<ITreePattern> _patterns;
    
    // ========================================
    
    public TreeMultiPattern( ITreePattern... patterns )
    {
        _patterns = Lists.newArrayList( patterns );
    }
    
    // ========================================
    
    @Override
    public boolean matches( ParserRuleContext ctx )
    {
        for ( ITreePattern pattern : _patterns )
        {
            if ( pattern.matches( ctx ) )
                return true;
        }
        
        return false;
    }
    
    @Override
    public <T extends ParserRuleContext> T get( ParserRuleContext ctx )
    {
        throw new UnsupportedOperationException();
    }
}
