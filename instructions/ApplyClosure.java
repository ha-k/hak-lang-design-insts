//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\
// PLEASE DO NOT EDIT WITHOUT THE EXPLICIT CONSENT OF THE AUTHOR! \\
//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\

package hlt.language.design.instructions;

/**
 * @version     Last modified on Wed Jun 20 14:29:51 2012 by hak
 * @author      <a href="mailto:hak@acm.org">Hassan A&iuml;t-Kaci</a>
 * @copyright   &copy; <a href="http://www.hassan-ait-kaci.net/">by the author</a>
 */

import hlt.language.design.backend.Runtime;
import hlt.language.design.backend.Closure;
import hlt.language.design.backend.NullValueException;

/**
 * A refinement of <a href="Apply.html"><tt>Apply</tt></a> that carries its own closure
 * as opposed of taking it from the stack.
 */
public class ApplyClosure extends Apply
{
  private Closure _closure;

  public ApplyClosure (Closure closure)
    {
      super(closure.voidArity(),closure.intArity(),closure.realArity(),closure.objectArity());
      _closure = closure;
      setName("ENTER_CLOSURE");
    }

  public void execute (Runtime r) throws NullValueException
    {
      if (_closure == null)
        throw new NullValueException("can't apply a null function");
      _execute(_closure,r);
    }
}
