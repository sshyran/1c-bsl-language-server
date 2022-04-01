/*
 * This file is a part of BSL Language Server.
 *
 * Copyright (c) 2018-2022
 * Alexey Sosnoviy <labotamy@gmail.com>, Nikita Fedkin <nixel2007@gmail.com> and contributors
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
package com.github._1c_syntax.bsl.languageserver.providers;

import com.github._1c_syntax.bsl.languageserver.commands.CommandSupplier;
import lombok.RequiredArgsConstructor;
import org.eclipse.lsp4j.ExecuteCommandParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class CommandProvider {

  @Autowired
  @Qualifier("commandSuppliersById")
  private Map<String, CommandSupplier> commandSuppliersById;

  private final CodeLensProvider codeLensProvider;
  private final InlayHintProvider inlayHintProvider;

  public Object executeCommand(ExecuteCommandParams params) {
    var commandId = params.getCommand();
    var commandSupplier = commandSuppliersById.getOrDefault(commandId, arguments -> Optional.empty());
    var result = commandSupplier
      .execute(params.getArguments())
      .orElse(null);

    CompletableFuture.runAsync(() -> {
      if (commandSupplier.refreshInlayHintsAfterExecuteCommand()) {
        inlayHintProvider.refreshInlayHints();
      }
      if (commandSupplier.refreshCodeLensesAfterExecuteCommand()) {
        codeLensProvider.refreshCodeLenses();
      }
    });

    return result;
  }

  public List<String> getCommandIds() {
    return List.copyOf(commandSuppliersById.keySet());
  }
}
