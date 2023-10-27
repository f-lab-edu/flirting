package site.ymango;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

public class DatabaseClearExtension implements BeforeEachCallback {
  @Override
  public void beforeEach(ExtensionContext context) {
    getDatabaseCleaner(context).clear();
  }

  private DatabaseCleaner getDatabaseCleaner(ExtensionContext extensionContext) {
    return SpringExtension.getApplicationContext(extensionContext)
        .getBean(DatabaseCleaner.class);
  }
}
