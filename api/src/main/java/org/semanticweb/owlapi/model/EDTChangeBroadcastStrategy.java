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

package org.semanticweb.owlapi.model;

import java.util.List;

import javax.swing.SwingUtilities;

/**
 * Author: Matthew Horridge<br> The University of Manchester<br> Information Management Group<br>
 * Date: 14-Mar-2009
 * <p/>
 * A change broadcast strategy which broadcasts all ontology changes in the Swing Even Dispatch Thread (EDT).
 */
public class EDTChangeBroadcastStrategy implements OWLOntologyChangeBroadcastStrategy {

    private static final long serialVersionUID = 30402L;

    @Override
    public void broadcastChanges(final OWLOntologyChangeListener listener, final List<? extends OWLOntologyChange> changes) throws OWLException {
        if (SwingUtilities.isEventDispatchThread()) {
            listener.ontologiesChanged(changes);
        }
        else {
            try {
                Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            listener.ontologiesChanged(changes);
                        } catch (OWLException e) {
                            throw new BroadcastException(e);
                        }
                    }
                };
                SwingUtilities.invokeLater(r);
            } catch (BroadcastException e) {
                throw (OWLException) e.getCause();
            }
        }
    }

    /** bit of a roundabout way to wrap an exception as a runtime exception, for
     * unwrapping later on */
    private static class BroadcastException extends RuntimeException {

        private static final long serialVersionUID = 30402L;

        BroadcastException(OWLException cause) {
            super(cause);
        }
    }
}
