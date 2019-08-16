//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\
// PLEASE DO NOT EDIT WITHOUT THE EXPLICIT CONSENT OF THE AUTHOR! \\
//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\

package hlt.language.design.instructions;

/**
 * @version     Last modified on Wed Jun 20 14:29:51 2012 by hak
 * @author      <a href="mailto:hak@acm.org">Hassan A&iuml;t-Kaci</a>
 * @copyright   &copy; <a href="http://www.hassan-ait-kaci.net/">by the author</a>
 */

import hlt.language.design.types.Type;
import hlt.language.design.types.FunctionType;
import hlt.language.design.backend.Runtime;
import hlt.language.design.backend.Closure;
import hlt.language.design.backend.NullValueException;

/**
 * An <tt>Apply</tt> instruction is to an <a href="Enter.html"><tt>Enter</tt></a>
 * instruction what an <a href="../kernel/Abstraction.html"><tt>Abstraction</tt></a>
 * expression is to a <a href="../kernel/Scope.html"><tt>Scope</tt></a> expression.
 * In other words, it is an <tt>Enter</tt> using a frame machinery.
 */
public class Apply extends Enter
{
  private boolean _LCO_isOff = true;

  protected Apply ()
    {
    }

  public Apply (int voidArity, int intArity, int realArity, int objectArity)
    {
      super(voidArity,intArity,realArity,objectArity);
      setName("APPLY");
    }

  public Apply (FunctionType type)
    {
      super(type);
      setName("APPLY");
    }

  public final Enter setLCO ()
    {
      setName("LCO_APPLY");
      _LCO_isOff = false;
      return this;
    }

  public final Apply curryObject ()
    {
      _objectArity--;
      _arity--;
      return this;
    }

  public void execute (Runtime r) throws NullValueException
    {
      _execute((Closure)r.popObject("can't apply a null function"),r);
    }

  protected final boolean _isVacuous (Closure c)
    {
      return super._isVacuous(c)
          && _voidArity == c.voidArity();
    }

  protected final void _execute (Closure c, Runtime r) throws NullValueException
    {
      if (_isVacuous(c))
        {
          r.incIP();
          return;
        }

      if (trueArity() == c.trueArity())
        {
          if (c.isExitable())
            // necessarily LCO is off (see ../kernel/Compiler.java)
            r.enterExitableScope(_intArity,_realArity,_objectArity);
          else
            if (_LCO_isOff)
              r.saveState(_intArity,_realArity,_objectArity);

          r.pushIntEnvArray(c.intFrame());
          r.pushRealEnvArray(c.realFrame());
          r.pushObjectEnvArray(c.objectFrame());

          if (_arity > 0)
            {
              for (int i=0; i<_intArity; i++)
                r.pushIntEnv(r.popInt());

              for (int i=0; i<_realArity; i++)
                r.pushRealEnv(r.popReal());

              for (int i=0; i<_objectArity; i++)
                r.pushObjectEnv(r.popObject());
            }

          r.setCode(c.code());
          r.setIP(c.address());
        }
      else // Type-checking guarantees that trueArity() < c.trueArity().
        {
          int[] intEnv = c.intFrame();
          double[] realEnv = c.realFrame();
          Object[] objectEnv = c.objectFrame();

          if (_intArity <= c.intArity())
            {
              intEnv = new int[_intArity+intEnv.length];
              for (int i=0; i<_intArity; i++)
                intEnv[i] = r.popInt();
              for (int i=_intArity; i<intEnv.length; i++)
                intEnv[i] = c.intFrame()[i-_intArity];
            }

          if (_realArity <= c.realArity())
            {
              realEnv = new double[_realArity+realEnv.length];
              for (int i=0; i<_realArity; i++)
                realEnv[i] = r.popReal();
              for (int i=_realArity; i<realEnv.length; i++)
                realEnv[i] = c.realFrame()[i-_realArity];
            }

          if (_objectArity <= c.objectArity())
            {
              objectEnv = new Object[_objectArity+objectEnv.length];
              for (int i=0; i<_objectArity; i++)
                objectEnv[i] = r.popObject();
              for (int i=_objectArity; i<objectEnv.length; i++)
                objectEnv[i] = c.objectFrame()[i-_objectArity];
            }

          r.pushObject(new Closure(c.isExitable(),
                                   c.code(),
                                   c.address(),
                                   c.voidArity() - _voidArity,
                                   c.intArity() - _intArity,
                                   c.realArity() - _realArity,
                                   c.objectArity() - _objectArity,
                                   intEnv,
                                   realEnv,
                                   objectEnv));
          r.incIP();
        }
    }

  public final boolean equals (Object object)
    {
      if (this == object)
        return true;

      if (!(object instanceof Apply))
        return false;

      Apply apply = (Apply)object;

      return _name        == apply.name()
          && _voidArity   == apply.voidArity()
          && _intArity    == apply.intArity()
          && _realArity   == apply.realArity()
          && _objectArity == apply.objectArity()
          && _LCO_isOff   == apply.lcoIsOff();
    }

}
