package com.example.demo;
import org.hibernate.query.criteria.internal.expression.function.LengthFunction;

import javax.persistence.*;

@Entity
@Table(name = "linkedInProfileScraper")
public class model {
    @Id
    public String url;
    @Column(length=3000)
    public String Experience;
    @Column(length=3000)
    public String Education;

    public model () {

    }

    public model (String url, String Experience, String Education) {
        this.url = url;
        this.Experience = Experience;
        this.Education = Education;
    }

    public String getUrl () {
        return this.url;
    }

    public String getExperience () {
        return this.Experience;
    }

    public String getEducation () {
        return this.Education;
    }

    public void setUrl (String url) {
        this.url = url;
    }

    public void setExperience (String Experience) {
        this.Experience = Experience;
    }

    public void setEducation (String Education) {
        this.Education = Education;
    }

    public String toString() {return String.format("URL:\n'%s'\n \n Experience:\n'%s'\n\n Education:\n'%s'", this.url, this.Experience, this.Education);}

}
