package kendoui.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;

import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * This is the default english text that is linked into the text for other locales. Default locale should be set in the
 * application configuration.
 */
@Entity
@Table(name = "DEFAULT_TEXT")
public class DefaultText implements Serializable {

    private static final long serialVersionUID = -7496867103742713462L;

    public DefaultText() {
    }

    public DefaultText(String text) {
        super();
        this.text = text;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DEFAULT_TEXT_GENERATOR")
    @SequenceGenerator(name = "DEFAULT_TEXT_GENERATOR", sequenceName = "DEFAULT_TEXT_SEQUENCE", allocationSize = 1)
    @Column(length = 32, name = "ID")
    private long id;

    @Column(nullable = false)
    private String text;

    @Version
    @Column(name = "lastupdated")
    private Timestamp lastUpdated;

    /**
     * Get the lastUpdated of Model
     *
     * @return the lastUpdated {@link java.sql.Timestamp}
     */
    public Timestamp getLastUpdated() {
        return lastUpdated;
    }

    /**
     * Set the value lastUpdated for Model
     *
     * @param lastUpdated the lastUpdated to set
     */
    public void setLastUpdated(Timestamp lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    /**
     * Get the id of DefaultText
     *
     * @return the id {@link long}
     */
    public long getId() {
        return id;
    }

    /**
     * Set the value id for DefaultText
     *
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Get the text of DefaultText
     *
     * @return the text {@link String}
     */
    public String getText() {
        return text;
    }

    /**
     * Set the value text for DefaultText
     *
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(getClass()).append(getId()).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof DefaultText) {
            return ((DefaultText) obj).getId() == getId();
        }
        return false;
    }
}
