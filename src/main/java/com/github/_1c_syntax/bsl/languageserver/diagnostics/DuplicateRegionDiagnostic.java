/*
 * This file is a part of BSL Language Server.
 *
 * Copyright © 2018-2020
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
package com.github._1c_syntax.bsl.languageserver.diagnostics;

import com.github._1c_syntax.bsl.languageserver.context.symbol.RegionSymbol;
import com.github._1c_syntax.bsl.languageserver.diagnostics.metadata.DiagnosticCompatibilityMode;
import com.github._1c_syntax.bsl.languageserver.diagnostics.metadata.DiagnosticInfo;
import com.github._1c_syntax.bsl.languageserver.diagnostics.metadata.DiagnosticMetadata;
import com.github._1c_syntax.bsl.languageserver.diagnostics.metadata.DiagnosticSeverity;
import com.github._1c_syntax.bsl.languageserver.diagnostics.metadata.DiagnosticTag;
import com.github._1c_syntax.bsl.languageserver.diagnostics.metadata.DiagnosticType;
import com.github._1c_syntax.bsl.languageserver.utils.Keywords;
import com.github._1c_syntax.bsl.languageserver.utils.Ranges;
import com.github._1c_syntax.bsl.languageserver.utils.RelatedInformation;
import com.github._1c_syntax.bsl.parser.BSLParser;
import org.antlr.v4.runtime.tree.ParseTree;
import org.eclipse.lsp4j.DiagnosticRelatedInformation;
import org.eclipse.lsp4j.Range;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@DiagnosticMetadata(
  type = DiagnosticType.CODE_SMELL,
  severity = DiagnosticSeverity.INFO,
  minutesToFix = 1,
  tags = {
    DiagnosticTag.STANDARD
  },
  compatibilityMode = DiagnosticCompatibilityMode.COMPATIBILITY_MODE_8_3_1
)
public class DuplicateRegionDiagnostic extends AbstractVisitorDiagnostic {
  private final Map<String, String> regionNames = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

  public DuplicateRegionDiagnostic(DiagnosticInfo info) {
    super(info);
    regionNames.put(Keywords.PUBLIC_REGION_RU, Keywords.PUBLIC_REGION_EN);
    regionNames.put(Keywords.PUBLIC_REGION_EN, Keywords.PUBLIC_REGION_EN);
    regionNames.put(Keywords.INTERNAL_REGION_RU, Keywords.INTERNAL_REGION_EN);
    regionNames.put(Keywords.INTERNAL_REGION_EN, Keywords.INTERNAL_REGION_EN);
    regionNames.put(Keywords.PRIVATE_REGION_RU, Keywords.PRIVATE_REGION_EN);
    regionNames.put(Keywords.PRIVATE_REGION_EN, Keywords.PRIVATE_REGION_EN);
    regionNames.put(Keywords.EVENTHANDLERS_REGION_RU, Keywords.EVENTHANDLERS_REGION_EN);
    regionNames.put(Keywords.EVENTHANDLERS_REGION_EN, Keywords.EVENTHANDLERS_REGION_EN);
    regionNames.put(Keywords.FORMEVENTHANDLERS_REGION_RU, Keywords.FORMEVENTHANDLERS_REGION_EN);
    regionNames.put(Keywords.FORMEVENTHANDLERS_REGION_EN, Keywords.FORMEVENTHANDLERS_REGION_EN);
    regionNames.put(Keywords.FORMHEADERITEMSEVENTHANDLERS_REGION_RU, Keywords.FORMHEADERITEMSEVENTHANDLERS_REGION_EN);
    regionNames.put(Keywords.FORMHEADERITEMSEVENTHANDLERS_REGION_EN, Keywords.FORMHEADERITEMSEVENTHANDLERS_REGION_EN);
    regionNames.put(Keywords.FORMCOMMANDSEVENTHANDLERS_REGION_RU, Keywords.FORMCOMMANDSEVENTHANDLERS_REGION_EN);
    regionNames.put(Keywords.FORMCOMMANDSEVENTHANDLERS_REGION_EN, Keywords.FORMCOMMANDSEVENTHANDLERS_REGION_EN);
    regionNames.put(Keywords.VARIABLES_REGION_RU, Keywords.VARIABLES_REGION_EN);
    regionNames.put(Keywords.VARIABLES_REGION_EN, Keywords.VARIABLES_REGION_EN);
    regionNames.put(Keywords.INITIALIZE_REGION_RU, Keywords.INITIALIZE_REGION_EN);
    regionNames.put(Keywords.INITIALIZE_REGION_EN, Keywords.INITIALIZE_REGION_EN);
  }

  @Override
  public ParseTree visitFile(BSLParser.FileContext ctx) {

    List<RegionSymbol> regions = documentContext.getFileLevelRegions();

    // анализировать модуль без областей не будем
    if (regions.isEmpty()) {
      return ctx;
    }

    // считаем дубли с учетом альтернативных имен для стандартных областей
    regions.stream()
      .collect(Collectors.groupingBy(regionSymbol ->
        regionNames.getOrDefault(regionSymbol.getName(), regionSymbol.getName())))
      .forEach((String name, List<RegionSymbol> regionsList) -> {
          if (regionsList.size() > 1) {

            List<DiagnosticRelatedInformation> relatedInformation = new ArrayList<>();
            RegionSymbol currentRegion = regionsList.get(0);
            Range currentRange = Ranges.create(currentRegion.getStartNode().getStart(),
              currentRegion.getStartNode().getStop());

            regionsList.stream()
              .map(region ->
                RelatedInformation.create(
                  documentContext.getUri(),
                  Ranges.create(region.getStartNode().getStart(), region.getStartNode().getStop()),
                  "+1"
                )
              )
              .collect(Collectors.toCollection(() -> relatedInformation));

            diagnosticStorage.addDiagnostic(
              currentRange,
              info.getMessage(currentRegion.getName()),
              relatedInformation);
          }
        }
      );

    return ctx;
  }
}
