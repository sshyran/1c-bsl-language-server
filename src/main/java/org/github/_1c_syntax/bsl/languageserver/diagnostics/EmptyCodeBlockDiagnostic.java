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

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.antlr.v4.runtime.tree.Tree;
import org.antlr.v4.runtime.tree.Trees;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.util.Ranges;
import org.github._1c_syntax.bsl.languageserver.diagnostics.metadata.DiagnosticMetadata;
import org.github._1c_syntax.bsl.languageserver.diagnostics.metadata.DiagnosticParameter;
import org.github._1c_syntax.bsl.languageserver.diagnostics.metadata.DiagnosticSeverity;
import org.github._1c_syntax.bsl.languageserver.diagnostics.metadata.DiagnosticType;
import org.github._1c_syntax.bsl.languageserver.utils.RangeHelper;
import org.github._1c_syntax.bsl.parser.BSLParser;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@DiagnosticMetadata(
  type = DiagnosticType.CODE_SMELL,
  severity = DiagnosticSeverity.MAJOR,
  minutesToFix = 5
)
public class EmptyCodeBlockDiagnostic extends AbstractVisitorDiagnostic {

  private static final boolean DEFAULT_COMMENT_AS_CODE = false;

  @DiagnosticParameter(
    type = Boolean.class,
    defaultValue = "" + DEFAULT_COMMENT_AS_CODE,
    description = "Считать комментарий в блоке кодом"
  )
  private boolean commentAsCode = DEFAULT_COMMENT_AS_CODE;

  @Override
  public void configure(Map<String, Object> configuration) {
    if (configuration == null) {
      return;
    }
    commentAsCode = Boolean.parseBoolean(configuration.get("commentAsCode").toString());
  }

  @Override
  public ParseTree visitCodeBlock(BSLParser.CodeBlockContext ctx) {

    if (ctx.getParent() instanceof BSLParser.FileContext
      || ctx.getParent() instanceof BSLParser.FileCodeBlockBeforeSubContext
      || ctx.getParent() instanceof BSLParser.FileCodeBlockContext
      || ctx.getParent() instanceof BSLParser.SubCodeBlockContext
      || ctx.getParent() instanceof BSLParser.ExceptCodeBlockContext) {
      return super.visitCodeBlock(ctx);
    }

    if (ctx.getChildCount() > 0) {
      return super.visitCodeBlock(ctx);
    }

    if(commentAsCode) {
      Stream<Token> comments = documentContext.getComments().stream();
      Range rangeCodeBlock = RangeHelper.newRange(ctx.getStop(), ctx.getStart());
      if(comments.anyMatch(token ->
        Ranges.containsRange(
          rangeCodeBlock,
          RangeHelper.newRange(token)))) {
        return super.visitCodeBlock(ctx);
      }
    }

    int lineOfStop = ctx.getStop().getLine();

    List<Tree> list = Trees.getChildren(ctx.getParent()).stream()
      .filter(node -> node instanceof TerminalNode)
      .filter(node -> ((TerminalNode) node).getSymbol().getLine() == lineOfStop)
      .collect(Collectors.toList());

    if (!list.isEmpty()) {
      TerminalNode first = (TerminalNode) list.get(0);
      TerminalNode last = (TerminalNode) list.get(list.size() - 1);

      diagnosticStorage.addDiagnostic(
        first.getSymbol().getLine() - 1,
        first.getSymbol().getCharPositionInLine(),
        last.getSymbol().getLine() - 1,
        last.getSymbol().getCharPositionInLine() + last.getText().length()
      );
    } else {
      diagnosticStorage.addDiagnostic(ctx.getParent().getStop());
    }

    return super.visitCodeBlock(ctx);
  }
}
