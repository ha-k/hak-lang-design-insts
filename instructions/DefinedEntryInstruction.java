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
import hlt.language.design.types.DefinedEntry;

abstract public class DefinedEntryInstruction extends Instruction
{
  protected DefinedEntry _entry;

  protected DefinedEntryInstruction (DefinedEntry entry)
    {
      _entry = entry;
    }

  final DefinedEntry entry ()
    {
      return _entry;
    }

  public final boolean equals (Object object)
    {
      if (this == object)
        return true;

      if (!(object instanceof DefinedEntryInstruction))
        return false;

      DefinedEntryInstruction other = (DefinedEntryInstruction)object;

      return name() == other.name() && _entry == other.entry();
    }

  public final int hashCode ()
    {
      return name().hashCode() + _entry.hashCode();
    }

  public final String toString ()
    {
      Type.resetNames();
      return name() + "(" + _entry + ")";
    }
}
