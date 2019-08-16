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
import hlt.language.design.backend.Block;
import hlt.language.design.backend.NullValueException;

/**
 * A refinement of <a href="Enter.html"><tt>Enter</tt></a> that carries its own block
 * as opposed of taking it from the stack.
 */
public class EnterBlock extends Enter
{
  private Block _block;

  public EnterBlock (Block block)
    {
      super(block.voidArity(),block.intArity(),block.realArity(),block.objectArity());
      _block = block;
      setName("ENTER_BLOCK");
    }

  public void execute (Runtime r) throws NullValueException
    {
      if (_block == null)
        throw new NullValueException("can't enter a null scope");
      _execute(_block,r);
    }
}
