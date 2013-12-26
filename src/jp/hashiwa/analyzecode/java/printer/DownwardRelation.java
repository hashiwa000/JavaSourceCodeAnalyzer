package jp.hashiwa.analyzecode.java.printer;

class DownwardRelation extends Relation {
  private static String ROOT_PREFIX      = "+--- ";
  private static String EXTEND_PREFIX    = "<--- ";
  private static String IMPLEMENT_PREFIX = "<... ";

  DownwardRelation(String name) {
    super(name);
  }

  @Override
  protected String getRootPrefix() {
    return ROOT_PREFIX;
  }

  @Override
  protected String getExtendPrefix() {
    return EXTEND_PREFIX;
  }

  @Override
  protected String getImplementPrefix() {
    return IMPLEMENT_PREFIX;
  }

}
