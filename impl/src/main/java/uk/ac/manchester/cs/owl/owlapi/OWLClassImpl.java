/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, The University of Manchester
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2011, University of Manchester
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.ac.manchester.cs.owl.owlapi;

import java.io.Serializable;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

import org.semanticweb.owlapi.model.ClassExpressionType;
import org.semanticweb.owlapi.model.EntityType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLClassExpressionVisitor;
import org.semanticweb.owlapi.model.OWLClassExpressionVisitorEx;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLEntityVisitor;
import org.semanticweb.owlapi.model.OWLEntityVisitorEx;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLNamedObjectVisitor;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

/** Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 25-Oct-2006<br>
 * <br> */
@SuppressWarnings("javadoc")
public class OWLClassImpl extends OWLClassExpressionImpl implements OWLClass,
Serializable {
    private static final long serialVersionUID = 30402L;
    private final IRI iri;
    private final boolean isThing;
    private final boolean isNothing;

    public OWLClassImpl(IRI iri) {
        super();
        this.iri = iri;
        isThing = getIRI().equals(OWLRDFVocabulary.OWL_THING.getIRI());
        isNothing = getIRI().equals(OWLRDFVocabulary.OWL_NOTHING.getIRI());
    }

    @Override
    public boolean isTopEntity() {
        return isOWLThing();
    }

    @Override
    public boolean isBottomEntity() {
        return isOWLNothing();
    }

    @Override
    public ClassExpressionType getClassExpressionType() {
        return ClassExpressionType.OWL_CLASS;
    }

    @Override
    public OWLClassExpression getObjectComplementOf() {
        return getOWLDataFactory().getOWLObjectComplementOf(this);
    }

    @Override
    public EntityType<?> getEntityType() {
        return EntityType.CLASS;
    }

    @Override
    public <E extends OWLEntity> E getOWLEntity(EntityType<E> entityType) {
        return getOWLDataFactory().getOWLEntity(entityType, getIRI());
    }

    @Override
    public boolean isType(EntityType<?> entityType) {
        return getEntityType().equals(entityType);
    }

    @Override
    public String toStringID() {
        return iri.toString();
    }

    @Override
    public IRI getIRI() {
        return iri;
    }

    @Override
    public boolean isBuiltIn() {
        return isOWLThing() || isOWLNothing();
    }

    @Override
    public Set<OWLAxiom> getReferencingAxioms(OWLOntology ontology) {
        return ontology.getReferencingAxioms(this);
    }

    @Override
    public Set<OWLAxiom>
    getReferencingAxioms(OWLOntology ontology, boolean includeImports) {
        return ontology.getReferencingAxioms(this, includeImports);
    }

    @Override
    public boolean isAnonymous() {
        return false;
    }

    @Override
    public boolean isClassExpressionLiteral() {
        return true;
    }

    @Override
    public OWLClass asOWLClass() {
        return this;
    }

    @Override
    public boolean isOWLThing() {
        return isThing;
    }

    @Override
    public boolean isOWLNothing() {
        return isNothing;
    }

    @Override
    public OWLClassExpression getNNF() {
        return this;
    }

    @Override
    public Set<OWLClassExpression> asConjunctSet() {
        return Collections.singleton((OWLClassExpression) this);
    }

    @Override
    public boolean containsConjunct(OWLClassExpression ce) {
        return ce.equals(this);
    }

    @Override
    public Set<OWLClassExpression> asDisjunctSet() {
        return Collections.singleton((OWLClassExpression) this);
    }

    @Override
    public OWLClassExpression getComplementNNF() {
        return getOWLDataFactory().getOWLObjectComplementOf(this);
    }

    // XXX not in the interface
        @Deprecated
    public Set<OWLSubClassOfAxiom> getSubClassAxioms(OWLOntology ontology) {
        return ontology.getSubClassAxiomsForSubClass(this);
    }

    // XXX not in the interface
        @Deprecated
    public Set<OWLEquivalentClassesAxiom>
    getEquivalentClassesAxioms(OWLOntology ontology) {
        return ontology.getEquivalentClassesAxioms(this);
    }

    // XXX not in the interface
        @Deprecated
    public Set<OWLDisjointClassesAxiom> getDisjointClassesAxioms(OWLOntology ontology) {
        return ontology.getDisjointClassesAxioms(this);
    }

    // XXX not in the interface
        @Deprecated
    public Set<OWLDisjointUnionAxiom> getDisjointUnionAxioms(OWLOntology ontology) {
        return ontology.getDisjointUnionAxioms(this);
    }

    @Override
    public Set<OWLClassExpression> getSuperClasses(OWLOntology ontology) {
        Set<OWLClassExpression> result = new TreeSet<OWLClassExpression>();
        for (OWLSubClassOfAxiom axiom : getSubClassAxioms(ontology)) {
            result.add(axiom.getSuperClass());
        }
        return result;
    }

    @Override
    public Set<OWLClassExpression> getSuperClasses(Set<OWLOntology> ontologies) {
        Set<OWLClassExpression> result = new TreeSet<OWLClassExpression>();
        for (OWLOntology ont : ontologies) {
            result.addAll(getSuperClasses(ont));
        }
        return result;
    }

    @Override
    public Set<OWLClassExpression> getSubClasses(OWLOntology ontology) {
        Set<OWLClassExpression> result = new TreeSet<OWLClassExpression>();
        for (OWLSubClassOfAxiom axiom : ontology.getSubClassAxiomsForSuperClass(this)) {
            result.add(axiom.getSubClass());
        }
        return result;
    }

    @Override
    public Set<OWLClassExpression> getSubClasses(Set<OWLOntology> ontologies) {
        Set<OWLClassExpression> result = new TreeSet<OWLClassExpression>();
        for (OWLOntology ont : ontologies) {
            result.addAll(getSubClasses(ont));
        }
        return result;
    }

    @Override
    public Set<OWLClassExpression> getEquivalentClasses(OWLOntology ontology) {
        Set<OWLClassExpression> result = new TreeSet<OWLClassExpression>();
        for (OWLEquivalentClassesAxiom axiom : getEquivalentClassesAxioms(ontology)) {
            result.addAll(axiom.getClassExpressions());
        }
        // Don't have the class equivalent to itself
        result.remove(this);
        return result;
    }

    @Override
    public Set<OWLClassExpression> getEquivalentClasses(Set<OWLOntology> ontologies) {
        Set<OWLClassExpression> result = new TreeSet<OWLClassExpression>();
        for (OWLOntology ont : ontologies) {
            result.addAll(getEquivalentClasses(ont));
        }
        return result;
    }

    @Override
    public Set<OWLClassExpression> getDisjointClasses(OWLOntology ontology) {
        Set<OWLClassExpression> result = new TreeSet<OWLClassExpression>();
        for (OWLDisjointClassesAxiom axiom : getDisjointClassesAxioms(ontology)) {
            result.addAll(axiom.getClassExpressions());
        }
        // The disjoint classes will contain this class - remove it!
        result.remove(this);
        return result;
    }

    @Override
    public Set<OWLClassExpression> getDisjointClasses(Set<OWLOntology> ontologies) {
        Set<OWLClassExpression> result = new TreeSet<OWLClassExpression>();
        for (OWLOntology ont : ontologies) {
            result.addAll(getDisjointClasses(ont));
        }
        return result;
    }

    @Override
    public Set<OWLIndividual> getIndividuals(OWLOntology ontology) {
        Set<OWLIndividual> result = new TreeSet<OWLIndividual>();
        for (OWLClassAssertionAxiom ax : ontology.getClassAssertionAxioms(this)) {
            result.add(ax.getIndividual());
        }
        return result;
    }

    @Override
    public Set<OWLIndividual> getIndividuals(Set<OWLOntology> ontologies) {
        Set<OWLIndividual> result = new TreeSet<OWLIndividual>();
        for (OWLOntology ont : ontologies) {
            result.addAll(getIndividuals(ont));
        }
        return result;
    }

    @Override
    public Set<OWLAnnotation> getAnnotations(OWLOntology ontology) {
        return ImplUtils.getAnnotations(this, Collections.singleton(ontology));
    }

    @Override
    public Set<OWLAnnotationAssertionAxiom> getAnnotationAssertionAxioms(
            OWLOntology ontology) {
        return ImplUtils.getAnnotationAxioms(this, Collections.singleton(ontology));
    }

    @Override
    public Set<OWLAnnotation> getAnnotations(OWLOntology ontology,
            OWLAnnotationProperty annotationProperty) {
        return ImplUtils.getAnnotations(this, annotationProperty,
                Collections.singleton(ontology));
    }

    @Override
    public boolean isDefined(OWLOntology ontology) {
        return !ontology.getEquivalentClassesAxioms(this).isEmpty();
    }

    @Override
    public boolean isDefined(Set<OWLOntology> ontologies) {
        for (OWLOntology ont : ontologies) {
            if (isDefined(ont)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public OWLDataProperty asOWLDataProperty() {
        throw new OWLRuntimeException("Not a data property!");
    }

    @Override
    public OWLDatatype asOWLDatatype() {
        throw new OWLRuntimeException("Not a data type!");
    }

    @Override
    public OWLNamedIndividual asOWLNamedIndividual() {
        throw new OWLRuntimeException("Not an individual!");
    }

    @Override
    public OWLObjectProperty asOWLObjectProperty() {
        throw new OWLRuntimeException("Not an object property");
    }

    @Override
    public boolean isOWLClass() {
        return true;
    }

    @Override
    public boolean isOWLDataProperty() {
        return false;
    }

    @Override
    public boolean isOWLDatatype() {
        return false;
    }

    @Override
    public boolean isOWLNamedIndividual() {
        return false;
    }

    @Override
    public boolean isOWLObjectProperty() {
        return false;
    }

    @Override
    public OWLAnnotationProperty asOWLAnnotationProperty() {
        throw new OWLRuntimeException("Not an annotation property");
    }

    @Override
    public boolean isOWLAnnotationProperty() {
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            if (!(obj instanceof OWLClass)) {
                return false;
            }
            IRI otherIRI = ((OWLClass) obj).getIRI();
            return otherIRI.equals(iri);
        }
        return false;
    }

    @Override
    public void accept(OWLClassExpressionVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void accept(OWLEntityVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void accept(OWLObjectVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void accept(OWLNamedObjectVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public <O> O accept(OWLEntityVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    @Override
    public <O> O accept(OWLClassExpressionVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    @Override
    public <O> O accept(OWLObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    @Override
    protected int compareObjectOfSameType(OWLObject object) {
        OWLClass other = (OWLClass) object;
        return iri.compareTo(other.getIRI());
    }
}
