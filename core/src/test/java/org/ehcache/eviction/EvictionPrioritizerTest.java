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

package org.ehcache.eviction;

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.TimeUnit;
import org.ehcache.Cache;
import org.hamcrest.CoreMatchers;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author cdennis
 */
public class EvictionPrioritizerTest {
  
  @Test
  public void testLruOrdering() {
    Cache.Entry<String, String> a = mock(Cache.Entry.class);
    when(a.getLastAccessTime(any(TimeUnit.class))).thenReturn(0L);
    Cache.Entry<String, String> b = mock(Cache.Entry.class);
    when(b.getLastAccessTime(any(TimeUnit.class))).thenReturn(1L);
    assertThat(Collections.max(Arrays.asList(a, b), EvictionPrioritizer.LRU), is(a));
  }

  @Test
  public void testLfuOrdering() {
    Cache.Entry<String, String> a = mock(Cache.Entry.class);
    when(a.getHitRate(any(TimeUnit.class))).thenReturn(0.0f);
    Cache.Entry<String, String> b = mock(Cache.Entry.class);
    when(b.getHitRate(any(TimeUnit.class))).thenReturn(1.0f);
    assertThat(Collections.max(Arrays.asList(a, b), EvictionPrioritizer.LFU), is(a));
  }

  @Test
  public void testFifoOrdering() {
    Cache.Entry<String, String> a = mock(Cache.Entry.class);
    when(a.getCreationTime(any(TimeUnit.class))).thenReturn(0L);
    Cache.Entry<String, String> b = mock(Cache.Entry.class);
    when(b.getCreationTime(any(TimeUnit.class))).thenReturn(1L);
    assertThat(Collections.max(Arrays.asList(a, b), EvictionPrioritizer.FIFO), is(a));
  }
}
