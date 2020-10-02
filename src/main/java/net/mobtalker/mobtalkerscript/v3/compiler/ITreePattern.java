/*
 * SPDX-FileCopyrightText: 2013-2020 Chimaine, MobTalkerScript contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package net.mobtalker.mobtalkerscript.v3.compiler;

import org.antlr.v4.runtime.ParserRuleContext;

public interface ITreePattern
{
    boolean matches( ParserRuleContext ctx );
    
    <T extends ParserRuleContext> T get( ParserRuleContext ctx );
}