/*
 * Copyright Terracotta, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ehcache.config.xml;

import org.ehcache.internal.EhcacheProvider;
import org.ehcache.internal.HeapResourceCacheConfiguration;
import org.ehcache.spi.service.ServiceConfiguration;
import org.w3c.dom.Element;

import java.io.IOException;
import java.net.URI;
import java.net.URL;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

/**
 * @author Alex Snaps
 */
public class HeapResourceParser implements XmlConfigurationParser<EhcacheProvider> {

  private static final URI NAMESPACE = URI.create("http://www.ehcache.org/v3/cachingtier");
  private static final URL XML_SCHEMA = HeapResourceParser.class.getResource("/ehcache-cachingtier.xsd");

  @Override
  public Source getXmlSchema() throws IOException {
    return new StreamSource(XML_SCHEMA.openStream());
  }

  @Override
  public URI getNamespace() {
    return NAMESPACE;
  }

  @Override
  public ServiceConfiguration<EhcacheProvider> parse(final Element heapConfig) {
    final long maxOnHeapEntryCount = Long.parseLong(heapConfig.getAttribute("size"));
    return new HeapResourceCacheConfiguration(maxOnHeapEntryCount);
  }
}
