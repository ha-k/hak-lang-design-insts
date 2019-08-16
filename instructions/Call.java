//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\
// PLEASE DO NOT EDIT WITHOUT THE EXPLICIT CONSENT OF THE AUTHOR! \\
//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\

package hlt.language.design.instructions;

/**
 * @version     Last modified on Wed Jun 20 14:29:51 2012 by hak
 * @author      <a href="mailto:hak@acm.org">Hassan A&iuml;t-Kaci</a>
 * @copyright   &copy; <a href="http://www.hassan-ait-kaci.net/">by the author</a>
 */

import hlt.language.design.types.DefinedEntry;
import hlt.language.design.backend.Runtime;
import hlt.language.design.backend.UnsafeCodeException;

public class Call extends DefinedEntryInstruction
{
  public Call (DefinedEntry entry)
    {
      super(entry);
      setName("CALL");
    }

  public final void execute (Runtime r) throws UnsafeCodeException
    {
      r.pushCall(_entry);
    }
}
