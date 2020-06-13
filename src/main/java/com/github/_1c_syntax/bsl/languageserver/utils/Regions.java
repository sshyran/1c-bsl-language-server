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
package com.github._1c_syntax.bsl.languageserver.utils;

import com.github._1c_syntax.bsl.languageserver.configuration.Language;
import com.github._1c_syntax.mdclasses.metadata.additional.ModuleType;
import com.github._1c_syntax.utils.CaseInsensitivePattern;
import lombok.experimental.UtilityClass;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

@UtilityClass
public class Regions {

  private final Pattern PUBLIC_REGION_NAME =
    createPattern(Keywords.PUBLIC_REGION_RU, Keywords.PUBLIC_REGION_EN);

  private final Pattern INTERNAL_REGION_NAME =
    createPattern(Keywords.INTERNAL_REGION_RU, Keywords.INTERNAL_REGION_EN);

  private final Pattern PRIVATE_REGION_NAME =
    createPattern(Keywords.PRIVATE_REGION_RU, Keywords.PRIVATE_REGION_EN);

  private final Pattern EVENT_HANDLERS_REGION_NAME =
    createPattern(Keywords.EVENT_HANDLERS_REGION_RU, Keywords.EVENT_HANDLERS_REGION_EN);

  private final Pattern FORM_EVENT_HANDLERS_REGION_NAME =
    createPattern(Keywords.FORM_EVENT_HANDLERS_REGION_RU, Keywords.FORM_EVENT_HANDLERS_REGION_EN);

  private final Pattern FORM_HEADER_ITEMS_EVENT_HANDLERS_REGION_NAME =
    createPattern(Keywords.FORM_HEADER_ITEMS_EVENT_HANDLERS_REGION_RU,
      Keywords.FORM_HEADER_ITEMS_EVENT_HANDLERS_REGION_EN);

  private final Pattern FORM_TABLE_ITEMS_EVENT_HANDLERS_REGION_NAME =
    createPattern(Keywords.FORM_TABLE_ITEMS_EVENT_HANDLERS_REGION_START_RU,
      Keywords.FORM_TABLE_ITEMS_EVENT_HANDLERS_REGION_START_EN,
      "^(?:%s|%s)[\\wа-яёЁ]*$");

  private final Pattern FORM_COMMANDS_EVENT_HANDLERS_REGION_NAME =
    createPattern(Keywords.FORM_COMMANDS_EVENT_HANDLERS_REGION_RU, Keywords.FORM_COMMANDS_EVENT_HANDLERS_REGION_EN);

  private final Pattern VARIABLES_REGION_NAME =
    createPattern(Keywords.VARIABLES_REGION_RU,
      Keywords.VARIABLES_REGION_EN);

  private final Pattern INITIALIZE_REGION_NAME =
    createPattern(Keywords.INITIALIZE_REGION_RU, Keywords.INITIALIZE_REGION_EN);

  public Set<Pattern> getStandardRegionsPatternsByModuleType(ModuleType moduleType) {

    if (moduleType == ModuleType.UNKNOWN) {
      return Collections.emptySet();
    }

    Set<Pattern> standardRegions = new HashSet<>();

    switch (moduleType) {
      case FormModule:
        addFormModuleRegions(standardRegions);
        break;
      case ObjectModule:
      case RecordSetModule:
        addRecordSetRegions(standardRegions);
        break;
      case ValueManagerModule:
        standardRegions.add(VARIABLES_REGION_NAME);
        standardRegions.add(PUBLIC_REGION_NAME);
        standardRegions.add(EVENT_HANDLERS_REGION_NAME);
        standardRegions.add(INTERNAL_REGION_NAME);
        break;
      case CommonModule:
        standardRegions.add(PUBLIC_REGION_NAME);
        standardRegions.add(INTERNAL_REGION_NAME);
        break;
      case ApplicationModule:
      case ManagedApplicationModule:
      case OrdinaryApplicationModule:
        standardRegions.add(VARIABLES_REGION_NAME);
        standardRegions.add(PUBLIC_REGION_NAME);
        standardRegions.add(EVENT_HANDLERS_REGION_NAME);
        break;
      case CommandModule:
      case SessionModule:
      case HTTPServiceModule:
        standardRegions.add(EVENT_HANDLERS_REGION_NAME);
        break;
      case ExternalConnectionModule:
        standardRegions.add(PUBLIC_REGION_NAME);
        standardRegions.add(EVENT_HANDLERS_REGION_NAME);
        break;
      case ManagerModule:
        standardRegions.add(PUBLIC_REGION_NAME);
        standardRegions.add(EVENT_HANDLERS_REGION_NAME);
        standardRegions.add(INTERNAL_REGION_NAME);
        break;
      default:
        // для Unknown ничего
    }

    // у всех типов модулей есть такая область
    standardRegions.add(PRIVATE_REGION_NAME);
    return standardRegions;
  }

  public Set<String> getStandardRegionsNamesByModuleType(ModuleType moduleType, Language language) {
    if (language == Language.EN) {
      return new HashSet<>();
    }
    return getRussianStandardRegionNames(moduleType);

  }

  private static Set<String> getRussianStandardRegionNames(ModuleType moduleType) {
    Set<String> regionsName = new HashSet<>();
    switch (moduleType) {
      case FormModule:
        addFormModuleRegionsNames(regionsName);
        break;
      case ObjectModule:
      case RecordSetModule:
        addObjectAndRecordSetRegionsName(regionsName);
        break;
      case ValueManagerModule:
        addValueManageRegionsName(regionsName);
        break;
      case CommonModule:
        addCommonModuleRegionNames(regionsName);
        break;
      case ApplicationModule:
      case ManagedApplicationModule:
      case OrdinaryApplicationModule:
        addApplicationModulesRegionsNames(regionsName);
        break;
      case CommandModule:
      case SessionModule:
        addCommandAndSessionModulesRegionsNames(regionsName);
        break;
      case ExternalConnectionModule:
        addExternalConnectionRegionsNames(regionsName);
        break;
      case ManagerModule:
        addManagerModuleRegionsNames(regionsName);
        break;
      default:
        // для Unknown ничего
    }

    // у всех типов модулей есть такая область
    regionsName.add(Keywords.PRIVATE_REGION_RU);

    return regionsName;
  }

  private static void addManagerModuleRegionsNames(Set<String> regionsName) {
    regionsName.add(Keywords.PUBLIC_REGION_RU);
    regionsName.add(Keywords.EVENT_HANDLERS_REGION_RU);
    regionsName.add(Keywords.INTERNAL_REGION_RU);
  }

  private static void addExternalConnectionRegionsNames(Set<String> regionsName) {
    regionsName.add(Keywords.PUBLIC_REGION_RU);
    regionsName.add(Keywords.EVENT_HANDLERS_REGION_RU);
  }

  private static void addCommandAndSessionModulesRegionsNames(Set<String> regionsName) {
    regionsName.add(Keywords.EVENT_HANDLERS_REGION_RU);
  }

  private static void addApplicationModulesRegionsNames(Set<String> regionsName) {
    regionsName.add(Keywords.VARIABLES_REGION_RU);
    regionsName.add(Keywords.PUBLIC_REGION_RU);
    regionsName.add(Keywords.EVENT_HANDLERS_REGION_RU);
  }

  private static void addCommonModuleRegionNames(Set<String> regionsName) {
    regionsName.add(Keywords.PUBLIC_REGION_RU);
    regionsName.add(Keywords.INTERNAL_REGION_RU);
  }

  private static void addValueManageRegionsName(Set<String> regionsName) {
    regionsName.add(Keywords.VARIABLES_REGION_RU);
    regionsName.add(Keywords.PUBLIC_REGION_RU);
    regionsName.add(Keywords.EVENT_HANDLERS_REGION_RU);
    regionsName.add(Keywords.INTERNAL_REGION_RU);
  }

  private static void addObjectAndRecordSetRegionsName(Set<String> regionsName) {
    regionsName.add(Keywords.VARIABLES_REGION_RU);
    regionsName.add(Keywords.PUBLIC_REGION_RU);
    regionsName.add(Keywords.EVENT_HANDLERS_REGION_RU);
    regionsName.add(Keywords.INTERNAL_REGION_RU);
    regionsName.add(Keywords.INITIALIZE_REGION_RU);
  }

  private static void addFormModuleRegionsNames(Set<String> regionsName) {
    regionsName.add(Keywords.VARIABLES_REGION_RU);
    regionsName.add(Keywords.FORM_EVENT_HANDLERS_REGION_RU);
    regionsName.add(Keywords.FORM_HEADER_ITEMS_EVENT_HANDLERS_REGION_RU);
    regionsName.add(Keywords.FORM_TABLE_ITEMS_EVENT_HANDLERS_REGION_START_RU);
    regionsName.add(Keywords.FORM_COMMANDS_EVENT_HANDLERS_REGION_RU);
    regionsName.add(Keywords.INITIALIZE_REGION_RU);
  }


  private void addRecordSetRegions(Set<Pattern> standardRegions) {
    standardRegions.add(VARIABLES_REGION_NAME);
    standardRegions.add(PUBLIC_REGION_NAME);
    standardRegions.add(EVENT_HANDLERS_REGION_NAME);
    standardRegions.add(INTERNAL_REGION_NAME);
    standardRegions.add(INITIALIZE_REGION_NAME);
  }

  private void addFormModuleRegions(Set<Pattern> standardRegions) {
    standardRegions.add(VARIABLES_REGION_NAME);
    standardRegions.add(FORM_EVENT_HANDLERS_REGION_NAME);
    standardRegions.add(FORM_HEADER_ITEMS_EVENT_HANDLERS_REGION_NAME);
    standardRegions.add(FORM_TABLE_ITEMS_EVENT_HANDLERS_REGION_NAME);
    standardRegions.add(FORM_COMMANDS_EVENT_HANDLERS_REGION_NAME);
    standardRegions.add(INITIALIZE_REGION_NAME);
  }

  private static Pattern createPattern(String keywordRu, String keywordEn) {
    return createPattern(keywordRu, keywordEn, "^(?:%s|%s)$");
  }

  private static Pattern createPattern(String keywordRu, String keywordEn, String template) {
    return CaseInsensitivePattern.compile(
      String.format(template, keywordRu, keywordEn)
    );
  }

}
