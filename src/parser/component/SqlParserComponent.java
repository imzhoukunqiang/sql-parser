package parser.component;

import com.intellij.openapi.components.ApplicationComponent;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zkq on 2018/1/11 16:24.
 */
public class SqlParserComponent implements ApplicationComponent {
    private static final Logger LOGGER = LoggerFactory.getLogger(SqlParserComponent.class);

    public SqlParserComponent() {
    }

    @Override
    public void initComponent() {
    }

    @Override
    public void disposeComponent() { // TODO: insert parser.component disposal logic here
    }

    @Override
    @NotNull
    public String getComponentName() {
        return "SqlParserComponent";
    }
 }