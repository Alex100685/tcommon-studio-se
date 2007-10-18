/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.talend.core.model.properties;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.AbstractEnumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>User Module Authorization Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.talend.core.model.properties.PropertiesPackage#getUserModuleAuthorizationType()
 * @model
 * @generated
 */
public final class UserModuleAuthorizationType extends AbstractEnumerator {
    /**
     * The '<em><b>Dashboard</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Dashboard</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #DASHBOARD_LITERAL
     * @model name="Dashboard"
     * @generated
     * @ordered
     */
    public static final int DASHBOARD = 2;

    /**
     * The '<em><b>Job Conductor</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Job Conductor</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #JOB_CONDUCTOR_LITERAL
     * @model name="JobConductor" literal="Job Conductor"
     * @generated
     * @ordered
     */
    public static final int JOB_CONDUCTOR = 1;

    /**
     * The '<em><b>Dashboard</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #DASHBOARD
     * @generated
     * @ordered
     */
    public static final UserModuleAuthorizationType DASHBOARD_LITERAL = new UserModuleAuthorizationType(DASHBOARD, "Dashboard", "Dashboard");

    /**
     * The '<em><b>Job Conductor</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #JOB_CONDUCTOR
     * @generated
     * @ordered
     */
    public static final UserModuleAuthorizationType JOB_CONDUCTOR_LITERAL = new UserModuleAuthorizationType(JOB_CONDUCTOR, "JobConductor", "Job Conductor");

    /**
     * An array of all the '<em><b>User Module Authorization Type</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static final UserModuleAuthorizationType[] VALUES_ARRAY =
        new UserModuleAuthorizationType[] {
            DASHBOARD_LITERAL,
            JOB_CONDUCTOR_LITERAL,
        };

    /**
     * A public read-only list of all the '<em><b>User Module Authorization Type</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final List VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>User Module Authorization Type</b></em>' literal with the specified literal value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static UserModuleAuthorizationType get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            UserModuleAuthorizationType result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>User Module Authorization Type</b></em>' literal with the specified name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static UserModuleAuthorizationType getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            UserModuleAuthorizationType result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>User Module Authorization Type</b></em>' literal with the specified integer value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static UserModuleAuthorizationType get(int value) {
        switch (value) {
            case DASHBOARD: return DASHBOARD_LITERAL;
            case JOB_CONDUCTOR: return JOB_CONDUCTOR_LITERAL;
        }
        return null;
    }

    /**
     * Only this class can construct instances.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private UserModuleAuthorizationType(int value, String name, String literal) {
        super(value, name, literal);
    }

} //UserModuleAuthorizationType
