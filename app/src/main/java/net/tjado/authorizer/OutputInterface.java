package net.tjado.authorizer;

/**
 * Created by tm on 21.04.16.
 */
public interface OutputInterface {

    public enum Language{};
    public boolean setLanguage(OutputInterface.Language lang);
    public void sendText(String text) throws Exception;
    public void destruct() throws Exception;
}
