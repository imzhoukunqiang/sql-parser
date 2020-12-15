package com.github.imzhoukunqiang.parser.action;

import com.github.imzhoukunqiang.parser.sql.ParseResult;
import com.github.imzhoukunqiang.parser.sql.parser.SqlParser;
import com.github.imzhoukunqiang.parser.sql.parser.SqlParserBuilder;
import com.github.imzhoukunqiang.parser.utils.BasicFormatterImpl;
import com.github.imzhoukunqiang.parser.utils.ClipboardUtil;
import com.github.imzhoukunqiang.parser.utils.Commons;
import com.github.imzhoukunqiang.parser.utils.Icons;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.ui.popup.Balloon;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 *
 * @author zkq
 * @date 2018/1/11 16:31
 */
public class SqlParseAction extends AnAction {
    private static final Logger LOGGER = Logger.getLogger(SqlParseAction.class);
    private SqlParser tempParser = SqlParser.EMPTY_PARSER;
    private final BasicFormatterImpl sqlFormatter = new BasicFormatterImpl();
    private String tempSelectedText = "";
    protected boolean beautify = false;

    @Override
    public void update(AnActionEvent e) {
        Presentation presentation = e.getPresentation();
        String selectedText = getSelectedText(e);
        SqlParser parser = getParser(selectedText);
        if (parser.canParse()) {
            presentation.setEnabled(true);
            presentation.setIcon(null);
            presentation.setIcon(Icons.Mybatis);
        } else if (parser.hasNativeSql()) {
            LOGGER.debug("The selected text has no parameters ");
            presentation.setEnabled(true);
            presentation.setIcon(AllIcons.General.Warning);
        } else {
            LOGGER.debug("The selected text can't parse to sql.");
            presentation.setEnabled(false);
        }
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        String selectedText = getSelectedText(e);
        SqlParser parser = getParser(selectedText);
        ParseResult parseResult = parser.parse();
        /* String s = Messages.showInputDialog( project, parseResult.isSuccess() ? "Success !" : "Failed !", "Parse Result ", parseResult.isSuccess() ? Messages.getInformationIcon() : Messages.getWarningIcon(), parseResult.getResult(), new NonEmptyInputValidator() ); */
        String sql = parseResult.getResult();
        if (Commons.isNotEmpty(sql)) {
            MessageType messageType = parseResult.isSuccess() ? MessageType.INFO : MessageType.WARNING;
            if (beautify){
                sql = sqlFormatter.format(sql);
            }
            ClipboardUtil.setClipboardString(sql);
            showPopupBalloon(e.getData(PlatformDataKeys.EDITOR), sql, messageType, "Sql has been copied to the clipboard");
        }
    }

    @Nullable
    private String getSelectedText(@NotNull AnActionEvent e) {
        Editor editor = e.getData(PlatformDataKeys.EDITOR);
        if (editor == null) {
            return null;
        }
        return editor.getSelectionModel().getSelectedText();
    }

    @NotNull
    private SqlParser getParser(@Nullable String selectedText) {
        SqlParser parser;
        if (Objects.equals(this.tempSelectedText, selectedText)) {
            parser = this.tempParser;
        } else {
            parser = SqlParserBuilder.getParser(selectedText);
            this.tempParser = parser;
            this.tempSelectedText = selectedText;
        }
        return parser;
    }

    private void showPopupBalloon(final Editor editor, final String message, MessageType messageType, String title) {
        ApplicationManager.getApplication().invokeLater(() -> {
            JBPopupFactory factory = JBPopupFactory.getInstance();

            String msg = StringEscapeUtils.escapeHtml4(message);
            Balloon balloon = factory.createHtmlTextBalloonBuilder(msg, messageType, null)
                                     .setFadeoutTime(5000)
                                     .setTitle(title)
                                     .createBalloon();
            balloon.show(factory.guessBestPopupLocation(editor), Balloon.Position.below);
        });
    }

    //
}