package ch.steponline.core.model;

import java.io.Serializable;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Roland
 * Date: 09.04.14
 * Time: 13:50
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractEntityTextContainer<K extends Serializable,E extends AbstractVersionedEntity> extends AbstractVersionedEntity<K,E> {

    public abstract Set<AbstractTextContainer> getTextContainers();

    public abstract void setTextContainers(Set<AbstractTextContainer> textContainers);

    protected abstract AbstractTextContainer createTextContainer(String language);

    public String getShortName(String language) {
        AbstractTextContainer textContainer=getTextContainer(language);
        return textContainer==null?"":textContainer.getShortName();
    }

    public void setShortName(String language,String text) {
        AbstractTextContainer tx=getTextContainer(language);
        if (tx==null) {
            tx=createTextContainer(language);
        }
        tx.setShortName(text);
    }

    public String getLongName(String language) {
        AbstractTextContainer textContainer=getTextContainer(language);
        return textContainer==null?"":textContainer.getLongName();
    }

    public void setLongName(String language,String text) {
        AbstractTextContainer tx=getTextContainer(language);
        if (tx==null) {
            tx=createTextContainer(language);
        }
        tx.setLongName(text);
    }

    public String getAbbreviation(String language) {
        AbstractTextContainer textContainer=getTextContainer(language);
        return textContainer==null?"":textContainer.getAbbreviation();
    }

    public void setAbbreviation(String language,String text) {
        AbstractTextContainer tx=getTextContainer(language);
        if (tx==null) {
            tx=createTextContainer(language);
        }
        tx.setAbbreviation(text);
    }

    public String getDocumentation(String language) {
        AbstractTextContainer textContainer=getTextContainer(language);
        return textContainer==null?"":textContainer.getDocumentation();
    }

    public void setDocumentation(String language,String text) {
        AbstractTextContainer tx=getTextContainer(language);
        if (tx==null) {
            tx=createTextContainer(language);
        }
        tx.setDocumentation(text);
    }

    protected AbstractTextContainer getTextContainer(String language) {
        for (AbstractTextContainer textContainer : getTextContainers()) {
            if (language.equals(textContainer.getLanguageIsoCode())) return textContainer;
        }
        return null;
    }
}
