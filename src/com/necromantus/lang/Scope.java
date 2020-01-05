package com.necromantus.lang;

import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Scope class contains all variables and functions. Internally it has two lists:
 * functions and variables. all function just help to do stuff, they only access
 * those two lists. Variables list contains "nonsense" variable (null value) and
 * "_" variable which is used to ignore anything (like in Swift). So if you write
 * <pre>
 *     _ = call some_function_that_returns_something_that_you_want_to_ignore().
 * </pre>
 * than _ will remain being nonsense. But! Because Necromantus is functional language,
 * you won't often need it. Scope has {@code parentScope} which is used to get
 * variables and functions from parent scope.
 *
 * @version 1.0.3
 * @apiNote NEW in version 1.0.3! Parent scopes!
 * @apiNote btw i thought how people write long class docs. now i understand :).
 * @since Necromantus Version 0.4
 */
public class Scope {
    public Scope parentScope;
    public ArrayList<NMVariable> vars = new ArrayList<>();
    public ArrayList<NMFunction> funcs = new ArrayList<>();

    /**
     * Create scope with parentScope.
     *
     * @param parentScope parent scope.
     */
    public Scope(Scope parentScope) { // that's not copy constructor, intellij IDEA!
        vars.add(new NMVariable("nonsense", null));
        vars.add(new NMVariable("_", null));
        this.parentScope = parentScope;
    }

    /**
     * Returns <pre>true</pre> if variable exists, otherwise false.
     *
     * @param name name of variable to check.
     * @return boolean.
     */
    public boolean containsVar(String name) {
        for (NMVariable var : vars) {
            if (var.name.equals(name)) return true;
        }
        if(parentScope!=null) return parentScope.containsVar(name);
        return false;
    }

    /**
     * Returns variable with name.
     *
     * @param name name of variable to return.
     * @return NMVariable with name.
     * @see NMVariable
     */
    public NMVariable getVar(String name) {
        for (NMVariable var : vars) {
            if (var.name.equals(name)) return var;
        }
        if (parentScope != null && parentScope.containsVar(name)) return parentScope.getVar(name); // lhs of && executes first!
        throw new NoSuchElementException("NO VAR check before doing this things scope is unchecked " + name + ".");
    }


    private class IndexOfReturn {
        public int index;
        public boolean parentScope;
        public boolean error;

        public IndexOfReturn(int index, boolean parentScope, boolean error) {
            this.index = index;
            this.parentScope = parentScope;
            this.error = error;
        }
    }
    /**
     * gets index of variable with name.
     *
     * @param name name of variable.
     * @return index.
     */
    private IndexOfReturn indexOfVar(String name) {
        for (NMVariable var : vars) {
            if (var.name.equals(name)) return new IndexOfReturn(vars.indexOf(var), false, false);
        }
        if (parentScope != null && parentScope.containsVar(name)) {
            IndexOfReturn i = parentScope.indexOfVar(name);
            i.parentScope = true;
            return i;
        }
        return new IndexOfReturn(-1, false, true);
    }

    /**
     * Sets variable. Note: to set some variable in parent scope
     * set it in there.
     *
     * @param name  name of variable to set.
     * @param value value of variable to set.
     */
    public void setVar(String name, Object value) throws Exception {
        IndexOfReturn i = indexOfVar(name);
        if(i.error) throw new Exception("INTERNAL: Scope.indexOfVar returned error.");
        if(!i.parentScope) {
            NMVariable v = vars.get(i.index);
            if (!v.name.equals("_")) { // may have used just name but who cares?.
                v.setValue(value);
                vars.set(i.index, v);
            }
        } else parentScope.setVar(name, value);
    }

    public boolean containsFunc(String name) {
        for (NMFunction func : funcs) {
            if (func.name.equals(name)) return true;
        }
        if(parentScope!=null) return parentScope.containsFunc(name);
        return false;
    }

    public NMFunction getFunc(String name) {
        for (NMFunction func : funcs) {
            if (func.name.equals(name)) return func;
        }
        if (parentScope != null && parentScope.containsFunc(name)) return parentScope.getFunc(name);
        throw new NoSuchElementException("No FUNC, check before doing this things; scope is unchecked " + name + ".");
    }

//    private int indexOfFunc(String name) {
//        for (NMFunction func : funcs) {
//            if (func.name.equals(name)) return funcs.indexOf(func);
//        }
//        if (parentScope != null && parentScope.containsFunc(name)) return parentScope.indexOfFunc(name);
//        return -1;
//    }

//    public void setFunc(String name, Object value) {
//        NMFunction v = funcs.get(indexOfFunc(name));
//        v.setValue(value);
//        vars.set(indexOfVar(name), v);
//    }


}
