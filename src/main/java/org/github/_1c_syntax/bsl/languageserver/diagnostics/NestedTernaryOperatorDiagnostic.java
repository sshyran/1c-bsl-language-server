/*
 * This file is a part of BSL Language Server.
 *
 * Copyright © 2018-2019
 * Alexey Sosnoviy <labotamy@gmail.com>, Nikita Gryzlov <nixel2007@gmail.com> and contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 *
 * BSL Language Server is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 *
 * BSL Language Server is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with BSL Language Server.
 */
package org.github._1c_syntax.bsl.languageserver.diagnostics;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.Trees;
import org.github._1c_syntax.bsl.languageserver.diagnostics.metadata.DiagnosticMetadata;
import org.github._1c_syntax.bsl.languageserver.diagnostics.metadata.DiagnosticSeverity;
import org.github._1c_syntax.bsl.languageserver.diagnostics.metadata.DiagnosticType;
import org.github._1c_syntax.bsl.parser.BSLParser;
import org.github._1c_syntax.bsl.parser.BSLParserRuleContext;

import java.util.Collection;
import java.util.List;

@DiagnosticMetadata(
  type = DiagnosticType.CODE_SMELL,
  severity = DiagnosticSeverity.MAJOR,
  minutesToFix = 5
)
public class NestedTernaryOperatorDiagnostic extends AbstractVisitorDiagnostic {

  private void findNestedTernaryOperator(BSLParserRuleContext ctx, int skip) {
    Collection<ParseTree> nestedTernaryOperators = Trees.findAllRuleNodes(ctx, BSLParser.RULE_ternaryOperator);
    if (nestedTernaryOperators.size() > skip) {
      nestedTernaryOperators.stream()
        .skip(skip)
        .forEach(parseTree -> diagnosticStorage.addDiagnostic((BSLParserRuleContext) parseTree));
    }
  }

  @Override
  public ParseTree visitIfStatement(BSLParser.IfStatementContext ctx) {
    List<BSLParser.ExpressionContext> expressionContexts = ctx.expression();
    for (BSLParser.ExpressionContext expCtx : expressionContexts) {
      findNestedTernaryOperator(expCtx, 0);
    }
    return super.visitChildren(ctx);
  }

  @Override
  public ParseTree visitTernaryOperator(BSLParser.TernaryOperatorContext ctx) {
    findNestedTernaryOperator(ctx, 1);
    return super.visitTernaryOperator(ctx);
  }

}
