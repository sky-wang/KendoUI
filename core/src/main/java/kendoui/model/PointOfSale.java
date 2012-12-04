package kendoui.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;

import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * For separating the category trees into different point of sales for player. E.g. In-Game store vs. web store.
 */
@Entity
@Table(name = "POINT_OF_SALE")
public class PointOfSale implements Serializable {

    private static final long serialVersionUID = 3261210656265786526L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "POINT_OF_SALE_GENERATOR")
    @SequenceGenerator(name = "POINT_OF_SALE_GENERATOR", sequenceName = "POINT_OF_SALE_SEQUENCE", allocationSize = 1)
    @Column(length = 32, name = "ID")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Version
    @Column(name = "lastupdated")
    private Timestamp lastUpdated;

    @Column(nullable = false)
    private Boolean inUse = false;

    /**
     * Get the lastUpdated of Model
     *
     * @return the lastUpdated {@link Timestamp}
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
     * Get the id of PointOfSale
     *
     * @return the id {@link long}
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the value id for PointOfSale
     *
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the name of PointOfSale
     *
     * @return the name {@link String}
     */
    public String getName() {
        return name;
    }

    /**
     * Set the value name for PointOfSale
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the inUse state of PointOfSale
     *
     * @return the inUse state {@link String}
     */
    public Boolean getInUse() {
        return inUse;
    }

    /**
     * Set the value inUse for PointOfSale
     *
     * @param inUse the inUse to set
     */
    public void setInUse(Boolean inUse) {
        this.inUse = inUse;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(getClass()).append(getId()).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PointOfSale) {
            return ((PointOfSale) obj).getId() == getId();
        }
        return false;
    }
}
