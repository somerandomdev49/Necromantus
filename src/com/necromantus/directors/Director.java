package com.necromantus.directors;

import com.necromantus.lang.Scope;
import com.necromantus.runtime.Walker;

public abstract class Director {
    public abstract void fill(Walker scope);
}
