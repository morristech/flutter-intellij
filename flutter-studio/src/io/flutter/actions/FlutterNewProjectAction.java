/*
 * Copyright 2017 The Chromium Authors. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be
 * found in the LICENSE file.
 */
package io.flutter.actions;


import com.android.tools.idea.ui.wizard.StudioWizardDialogBuilder;
import com.android.tools.idea.wizard.model.ModelWizard;
import com.android.tools.idea.wizard.model.ModelWizardDialog;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.DumbAware;
import io.flutter.module.FlutterProjectType;
import io.flutter.project.ChoseProjectTypeStep;
import io.flutter.project.FlutterProjectModel;
import java.util.NoSuchElementException;
import org.jetbrains.annotations.NotNull;

public class FlutterNewProjectAction extends AnAction implements DumbAware {
  private static final Logger LOG = Logger.getInstance(FlutterNewProjectAction.class);

  public FlutterNewProjectAction() {
    this("New Flutter Project...");
  }

  public FlutterNewProjectAction(@NotNull String text) {
    super(text);
  }

  @Override
  public void update(@NotNull AnActionEvent e) {
    // This can be used to change the icon on the welcome screen, if needed.
  }

  @Override
  public void actionPerformed(AnActionEvent e) {
    FlutterProjectModel model = new FlutterProjectModel(FlutterProjectType.APP);
    ModelWizard wizard = new ModelWizard.Builder()
      .addStep(new ChoseProjectTypeStep(model))
      .build();
    StudioWizardDialogBuilder builder = new StudioWizardDialogBuilder(wizard, "Create New Flutter Project");
    builder.setUxStyle(StudioWizardDialogBuilder.UxStyle.DYNAMIC_APP);
    ModelWizardDialog dialog = builder.build();
    try {
      dialog.show();
    }
    catch (NoSuchElementException ex) {
      // This happens if no Flutter SDK is installed and the user cancels the FlutterProjectStep.
    }
  }
}
