//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\
// PLEASE DO NOT EDIT WITHOUT THE EXPLICIT CONSENT OF THE AUTHOR! \\
//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\

package hlt.language.design.instructions;

/**
 * @version     Last modified on Wed Jun 20 14:29:51 2012 by hak
 * @author      <a href="mailto:hak@acm.org">Hassan A&iuml;t-Kaci</a>
 * @copyright   &copy; <a href="http://www.hassan-ait-kaci.net/">by the author</a>
 */

/**
 * This class is the mother of all instructions that refer to an
 * object's field, whose offset must be set upon backpatching.
 */

import hlt.language.design.types.DefinedEntry;
import hlt.language.design.backend.Runtime;
import hlt.language.design.backend.ObjectInstance;
import hlt.language.design.backend.NullValueException;

public abstract class FieldInstruction extends Instruction
{
  private DefinedEntry _entry;

  protected final DefinedEntry entry ()
    {
      return _entry;
    }

  protected final int offset ()
    {
      return _entry.fieldOffset();
    }

  protected FieldInstruction (DefinedEntry codeEntry)
    {
      _entry = codeEntry;
    }

  public final ObjectInstance getObject (Runtime r, String mode) throws NullValueException
    {
      return (ObjectInstance)r.popObject("can't "+mode+" field '"+_entry.symbol()+
                                         "' of a null object");
    }      

  public final int hashCode ()
    {
      return name().hashCode() + _entry.hashCode();
    }

  public final boolean equals (Object object)
    {
      if (this == object)
        return true;

      if (!(object instanceof FieldInstruction))
        return false;

      FieldInstruction other = (FieldInstruction)object;

      return name() == other.name() && _entry == other.entry();
    }

  public final String toString ()
    {
      return name() + "(" + _entry + ")";
    }
}

