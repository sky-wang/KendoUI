package kendoui.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;

import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Which categories the PointOfSale belongs to. PointOfSale can belong to multiple categories.
 */
@Entity
@Table(name = "POINT_OF_SALE_CATEGORY")
public class PointOfSaleCategory implements Serializable {

    private static final long serialVersionUID = -4577207857839566760L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "POINT_OF_SALE_CATEGORY_GENERATOR")
    @SequenceGenerator(name = "POINT_OF_SALE_CATEGORY_GENERATOR", sequenceName = "POINT_OF_SALE_CATEGORY_SEQUENCE", allocationSize = 1)
    @Column(length = 32, name = "ID")
    private long id;

    private int pointOfSaleOrder;

    @ManyToOne(optional = false)
    @JoinColumn(name = "pointOfSale")
    private PointOfSale pointOfSale;

    @ManyToOne(optional = false)
    @JoinColumn(name = "category")
    private Category category;

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
     * Get the id of PointOfSaleCategory
     *
     * @return the id {@link long}
     */
    public long getId() {
        return id;
    }

    /**
     * Set the value id for PointOfSaleCategory
     *
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Get the pointOfSaleOrder of PointOfSaleCategory
     *
     * @return the pointOfSaleOrder {@link int}
     */
    public int getPointOfSaleOrder() {
        return pointOfSaleOrder;
    }

    /**
     * Set the value pointOfSaleOrder for PointOfSaleCategory
     *
     * @param pointOfSaleOrder the pointOfSaleOrder to set
     */
    public void setPointOfSaleOrder(int pointOfSaleOrder) {
        this.pointOfSaleOrder = pointOfSaleOrder;
    }

    /**
     * Get the pointOfSale of PointOfSaleCategory
     *
     * @return the pointOfSale {@link PointOfSale}
     */
    public PointOfSale getPointOfSale() {
        return pointOfSale;
    }

    /**
     * Set the value pointOfSale for PointOfSaleCategory
     *
     * @param pointOfSale the pointOfSale to set
     */
    public void setPointOfSale(PointOfSale pointOfSale) {
        this.pointOfSale = pointOfSale;
    }

    /**
     * Get the category of PointOfSaleCategory
     *
     * @return the category {@link Category}
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Set the value category for PointOfSaleCategory
     *
     * @param category the category to set
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(getClass()).append(getId()).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PointOfSaleCategory) {
            return ((PointOfSaleCategory) obj).getId() == getId();
        }
        return false;
    }
}
