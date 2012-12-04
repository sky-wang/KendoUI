package kendoui.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.*;

import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * All available categories.
 */
@Entity
@Table(name = "CATEGORY")
public class Category implements Serializable {

    private static final long serialVersionUID = -2639200307411049182L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CATEGORY_GENERATOR")
    @SequenceGenerator(name = "CATEGORY_GENERATOR", sequenceName = "CATEGORY_SEQUENCE", allocationSize = 1)
    @Column(length = 32, name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "name")
    private DefaultText name;

    @ManyToOne
    @JoinColumn(name = "description")
    private DefaultText description;

    @ManyToMany
    @JoinTable(name = "CATEGORY_TREE", joinColumns = @JoinColumn(name = "Child", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "Parent", referencedColumnName = "ID"))
    private Set<Category> parents;

    @ManyToMany
    @JoinTable(name = "CATEGORY_TREE", joinColumns = @JoinColumn(name = "Parent", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "Child", referencedColumnName = "ID"))
    private Set<Category> chidren;

    @Version
    @Column(name = "lastupdated")
    private Timestamp lastUpdated;

    @Column(nullable = false)
    private Boolean inUse = false;

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
     * Get the id of Category
     *
     * @return the id {@link long}
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the value id for Category
     *
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the name of Category
     *
     * @return the name {@link DefaultText}
     */
    public DefaultText getName() {
        return name;
    }

    /**
     * Set the value name for Category
     *
     * @param name the name to set
     */
    public void setName(DefaultText name) {
        this.name = name;
    }

    /**
     * Get the description of Category
     *
     * @return the description {@link DefaultText}
     */
    public DefaultText getDescription() {
        return description;
    }

    /**
     * Set the value description for Category
     *
     * @param description the description to set
     */
    public void setDescription(DefaultText description) {
        this.description = description;
    }

    /**
     * Get the parents of Category
     *
     * @return the parents {@link java.util.Set<Category>}
     */
    public Set<Category> getParents() {
        return parents;
    }

    /**
     * Set the value parents for Category
     *
     * @param parents the parents to set
     */
    public void setParents(Set<Category> parents) {
        this.parents = parents;
    }

    /**
     * Get the chidren of Category
     *
     * @return the chidren {@link java.util.Set<Category>}
     */
    public Set<Category> getChidren() {
        return chidren;
    }

    /**
     * Set the value chidren for Category
     *
     * @param chidren the chidren to set
     */
    public void setChidren(Set<Category> chidren) {
        this.chidren = chidren;
    }

    /**
     * Get the inUse state of Category
     *
     * @return the inUse state {@link String}
     */
    public Boolean getInUse() {
        return inUse;
    }

    /**
     * Set the value inUse for Category
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
        if (obj instanceof Category) {
            return ((Category) obj).getId() == getId();
        }
        return false;
    }
}
