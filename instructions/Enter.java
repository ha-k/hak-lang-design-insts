//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\
// PLEASE DO NOT EDIT WITHOUT THE EXPLICIT CONSENT OF THE AUTHOR! \\
//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\

package hlt.language.design.instructions;

/**
 * @version     Last modified on Sat Oct 20 21:19:57 2012 by hak
 * @author      <a href="mailto:hak@acm.org">Hassan A&iuml;t-Kaci</a>
 * @copyright   &copy; <a href="http://www.hassan-ait-kaci.net/">by the author</a>
 */

import hlt.language.design.types.Type;
import hlt.language.design.types.FunctionType;
import hlt.language.design.backend.Runtime;
import hlt.language.design.backend.Block;
import hlt.language.design.backend.NullValueException;

/**
 * An <tt>Enter</tt> instruction is to a <a href="../kernel/Scope.html">
 * <tt>Scope</tt></a> expression what an <a href="Apply.html">
 * <tt>Apply</tt></a> instruction is to an <a
 * href="../kernel/Abstraction.html"> <tt>Abstraction</tt></a>
 * expression. In other words, it is an <tt>Apply</tt> that does not use
 * any frame machinery.
 */
public class Enter extends Instruction
{
  protected int _voidArity;
  protected int _intArity;
  protected int _realArity;
  protected int _objectArity;
  protected int _arity;

  protected boolean _LCO_isOff = true;

  protected Enter ()
    {
    }

  public Enter (int voidArity, int intArity, int realArity, int objectArity)
    {
      setName("ENTER");
      _voidArity = voidArity;
      _intArity = intArity;
      _realArity = realArity;
      _objectArity = objectArity;
      _arity = _intArity + _realArity + _objectArity;
    }

  public Enter (FunctionType type)
    {
      setName("ENTER");
      for (int i=0; i<type.arity(); i++)
        switch (type.domain(i).boxSort())
          {
          case Type.INT_SORT:
            _intArity++;
            break;
          case Type.REAL_SORT:
            _realArity++;
            break;
          case Type.OBJECT_SORT:
           _objectArity++;
           break;
          default:
            _voidArity++;
          }

      _arity = _intArity + _realArity + _objectArity;
    }

  public final boolean lcoIsOff ()
    {
      return _LCO_isOff;
    }

  public Enter setLCO ()
    {
      setName("LCO_ENTER");
      _LCO_isOff = false;
      return this;
    }

  final int voidArity ()
    {
      return _voidArity;
    }

  final int intArity ()
    {
      return _intArity;
    }

  final int realArity ()
    {
      return _realArity;
    }

  final int objectArity ()
    {
      return _objectArity;
    }

  public final int arity ()
    {
      return _arity;
    }

  public final int trueArity ()
    {
      return _arity + _voidArity;
    }

  public void execute (Runtime r) throws NullValueException
    {
      _execute((Block)r.popObject("can't enter a null scope"),r);
    }

  protected final boolean _isVacuous (Block b)
    {
      return _arity == 0
          && b.code()[b.address()].isReturn();
    }

  protected final void _execute (Block b, Runtime r) throws NullValueException
    {
      // Type-checking guarantees that trueArity() == b.trueArity()

      if (_isVacuous(b))
        {
          r.incIP();
          return;
        }

      if (_LCO_isOff)
        r.saveState(_intArity,_realArity,_objectArity);

      if (_arity > 0)
        {
          for (int i=0; i<_intArity; i++)
            r.pushIntEnv(r.popInt());

          for (int i=0; i<_realArity; i++)
            r.pushRealEnv(r.popReal());

          for (int i=0; i<_objectArity; i++)
            r.pushObjectEnv(r.popObject());
        }

      r.setCode(b.code());
      r.setIP(b.address());
    }

  public boolean equals (Object object)
    {
      if (this == object)
        return true;

      if (!(object instanceof Enter))
        return false;

      Enter enter = (Enter)object;

      return _name        == enter.name()
          && _voidArity   == enter.voidArity()
          && _intArity    == enter.intArity()
          && _realArity   == enter.realArity()
          && _objectArity == enter.objectArity()
          && _LCO_isOff   == enter.lcoIsOff();
    }

  public final String toString ()
    {
      return _name + "(" + _voidArity + "," + _intArity + "," + _realArity + "," + _objectArity + ")";
    }
}
