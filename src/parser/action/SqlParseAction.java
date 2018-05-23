package parser.action;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.ui.popup.Balloon;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import icon.Icons;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parser.sql.ParseResult;
import parser.sql.parser.SqlParser;
import parser.sql.parser.SqlParserBuilder;
import parser.utils.ClipboardUtil;

/**
 * Created by zkq on 2018/1/11 16:31.
 */
public class SqlParseAction extends AnAction {
    private static final Logger LOGGER = LoggerFactory.getLogger(SqlParseAction.class);
    private Application application = ApplicationManager.getApplication();
    private SqlParser tempParser = SqlParser.EMPTY_PARSER;
    private String tempSelectedText = "";

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
        if (StringUtils.isNotEmpty(parseResult.getResult())) {
            MessageType messageType = parseResult.isSuccess() ? MessageType.INFO : MessageType.WARNING;
            ClipboardUtil.setClipboardString(parseResult.getResult());
            showPopupBalloon(e.getData(PlatformDataKeys.EDITOR), parseResult.getResult(), messageType, "Sql has been copied to the clipboard");
        }
    }

    @Nullable
    private String getSelectedText(@NotNull AnActionEvent e) {
        Editor editor = e.getData(PlatformDataKeys.EDITOR);
        if (editor == null) return null;
        return editor.getSelectionModel().getSelectedText();
    }

    @NotNull
    private SqlParser getParser(@Nullable String selectedText) {
        SqlParser parser;
        if (ObjectUtils.equals(this.tempSelectedText, selectedText)) {
            parser = this.tempParser;
        } else {
            parser = SqlParserBuilder.getParser(selectedText);
            this.tempParser = parser;
            this.tempSelectedText = selectedText;
        }
        return parser;
    }

    private void showPopupBalloon(final Editor editor, final String result, MessageType messageType, String title) {
        ApplicationManager.getApplication().invokeLater(() -> {
            JBPopupFactory factory = JBPopupFactory.getInstance();
            Balloon balloon = factory.createHtmlTextBalloonBuilder(StringEscapeUtils.escapeHtml(result), messageType, null)
                    .setFadeoutTime(5000)
                    .setTitle(title)
                    .createBalloon();
            balloon.show(factory.guessBestPopupLocation(editor), Balloon.Position.below);
        });
    }

    //
}