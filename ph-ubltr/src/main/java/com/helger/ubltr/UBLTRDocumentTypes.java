/**
 * Copyright (C) 2014-2016 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.helger.ubltr;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import javax.xml.validation.Schema;

import com.helger.commons.annotation.PresentForCodeCoverage;
import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.collection.ArrayHelper;
import com.helger.commons.collection.CollectionHelper;

/**
 * UBL document type map. Provides sanity methods for accessing UBLTR document
 * types based on different information items.
 *
 * @author Philip Helger
 */
@Immutable
public final class UBLTRDocumentTypes
{
  /** Maps local names to document types */
  private static final Map <String, EUBLTRDocumentType> s_aLocalName2DocType = new HashMap <> ();

  static
  {
    // Does not contain a namespace-to-doctype map because the namespace is not
    // unique!

    // Register all UBLTR document types
    for (final EUBLTRDocumentType eDocType : EUBLTRDocumentType.values ())
    {
      // add to local name map
      final String sLocalName = eDocType.getLocalName ();
      if (s_aLocalName2DocType.containsKey (sLocalName))
        throw new IllegalArgumentException ("The local name '" + sLocalName + "' is already mapped!");
      s_aLocalName2DocType.put (sLocalName, eDocType);
    }
  }

  @PresentForCodeCoverage
  private static final UBLTRDocumentTypes s_aInstance = new UBLTRDocumentTypes ();

  private UBLTRDocumentTypes ()
  {}

  /**
   * @return A non-<code>null</code> set of all supported UBLTR document element
   *         local names.
   */
  @Nonnull
  @ReturnsMutableCopy
  public static Set <String> getAllLocalNames ()
  {
    return CollectionHelper.newSet (s_aLocalName2DocType.keySet ());
  }

  /**
   * Get the document type matching the passed document element local name.
   *
   * @param sLocalName
   *        The document element local name of any UBLTR document type. May be
   *        <code>null</code>.
   * @return <code>null</code> if no UBLTR document type matching the specified
   *         local name exists.
   */
  @Nullable
  public static EUBLTRDocumentType getDocumentTypeOfLocalName (@Nullable final String sLocalName)
  {
    return s_aLocalName2DocType.get (sLocalName);
  }

  /**
   * Get the domain object class of the passed document element local name.
   *
   * @param sLocalName
   *        The document element local name of any UBLTR document type. May be
   *        <code>null</code>.
   * @return <code>null</code> if no such implementation class exists.
   */
  @Nullable
  public static Class <?> getImplementationClassOfLocalName (@Nullable final String sLocalName)
  {
    final EUBLTRDocumentType eDocType = getDocumentTypeOfLocalName (sLocalName);
    return eDocType == null ? null : eDocType.getImplementationClass ();
  }

  /**
   * Get the UBLTR document type matching the passed implementation class.
   *
   * @param aImplClass
   *        The implementation class to use. May be <code>null</code>.
   * @return <code>null</code> if the implementation class is <code>null</code>
   *         or if no UBLTR document type has the specified implementation
   *         class.
   */
  @Nullable
  public static EUBLTRDocumentType getDocumentTypeOfImplementationClass (@Nullable final Class <?> aImplClass)
  {
    if (aImplClass == null)
      return null;
    return ArrayHelper.findFirst (EUBLTRDocumentType.values (),
                                  eDocType -> eDocType.getImplementationClass ().equals (aImplClass));
  }

  /**
   * Get the XSD Schema object for the UBLTR document type of the passed
   * document element local name.
   *
   * @param sLocalName
   *        The document element local name of any UBLTR document type. May be
   *        <code>null</code>.
   * @return <code>null</code> if no such UBLTR document type exists.
   */
  @Nullable
  public static Schema getSchemaOfLocalName (@Nullable final String sLocalName)
  {
    return getSchemaOfLocalName (sLocalName, (ClassLoader) null);
  }

  /**
   * Get the XSD Schema object for the UBLTR document type of the passed
   * document element local name.
   *
   * @param sLocalName
   *        The document element local name of any UBLTR document type. May be
   *        <code>null</code>.
   * @param aClassLoader
   *        The class loader to be used. May be <code>null</code> indicating
   *        that the default class loader should be used.
   * @return <code>null</code> if no such UBLTR document type exists.
   */
  @Nullable
  public static Schema getSchemaOfLocalName (@Nullable final String sLocalName,
                                             @Nullable final ClassLoader aClassLoader)
  {
    final EUBLTRDocumentType eDocType = getDocumentTypeOfLocalName (sLocalName);
    return eDocType == null ? null : eDocType.getSchema (aClassLoader);
  }

  /**
   * Get the XSD Schema object for the UBLTR document type of the passed
   * implementation class.
   *
   * @param aImplClass
   *        The implementation class of any UBLTR document type. May be
   *        <code>null</code>.
   * @return <code>null</code> if no such UBLTR document type exists.
   */
  @Nullable
  public static Schema getSchemaOfImplementationClass (@Nullable final Class <?> aImplClass)
  {
    return getSchemaOfImplementationClass (aImplClass, (ClassLoader) null);
  }

  /**
   * Get the XSD Schema object for the UBLTR document type of the passed
   * implementation class.
   *
   * @param aImplClass
   *        The implementation class of any UBLTR document type. May be
   *        <code>null</code>.
   * @param aClassLoader
   *        The class loader to be used. May be <code>null</code> indicating
   *        that the default class loader should be used.
   * @return <code>null</code> if no such UBLTR document type exists.
   */
  @Nullable
  public static Schema getSchemaOfImplementationClass (@Nullable final Class <?> aImplClass,
                                                       @Nullable final ClassLoader aClassLoader)
  {
    final EUBLTRDocumentType eDocType = getDocumentTypeOfImplementationClass (aImplClass);
    return eDocType == null ? null : eDocType.getSchema (aClassLoader);
  }
}