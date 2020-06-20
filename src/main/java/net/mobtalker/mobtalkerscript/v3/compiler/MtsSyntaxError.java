/*
 * Copyright (C) 2013-2020 Chimaine, MobTalkerScript contributors
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

@SuppressWarnings( "serial" )
public class MtsSyntaxError extends RuntimeException
{
    private final String _sourceName;
    private final SourcePosition _sourcePosition;
    private final String _msg;
    
    // ========================================
    
    public MtsSyntaxError( String sourceName, SourcePosition sourcePosition, String msg )
    {
        _sourceName = sourceName;
        _sourcePosition = sourcePosition;
        _msg = msg;
    }
    
    public MtsSyntaxError( String sourceName, int line, int coloum, String msg )
    {
        _sourceName = sourceName;
        _sourcePosition = new SourcePosition( line, coloum );
        _msg = msg;
    }
    
    // ========================================
    
    public String getSourceName()
    {
        return _sourceName;
    }
    
    public SourcePosition getSourcePosition()
    {
        return _sourcePosition;
    }
    
    public String getOriginalMessage()
    {
        return _msg;
    }
    
    // ========================================
    
    @Override
    public String getMessage()
    {
        return new StringBuilder().append( _sourceName )
                                  .append( ":" )
                                  .append( _sourcePosition.Line )
                                  .append( ": " )
                                  .append( _msg ).toString();
    }
}
