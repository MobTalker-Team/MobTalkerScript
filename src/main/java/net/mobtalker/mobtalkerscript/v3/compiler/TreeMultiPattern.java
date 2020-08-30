/*
 * SPDX-FileCopyrightText: 2013-2020 Chimaine, MobTalkerScript contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
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
