/*
 * MIT License
 *
 * Copyright (c) 2021 MinecraftOnline
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package com.minecraftonline.nope.game.listener;

import com.minecraftonline.nope.setting.SettingKey;
import java.util.function.Predicate;
import javax.annotation.Nonnull;
import org.spongepowered.api.event.Cancellable;
import org.spongepowered.api.event.Event;

/**
 * An accessibility class for cancelling events.
 *
 * @param <E> the type of event to cancel
 */
class CancelConditionSettingListener<E extends Event & Cancellable>
    extends SingleSettingListener<E> {
  public CancelConditionSettingListener(@Nonnull SettingKey<?> key,
                                        @Nonnull Class<E> eventClass,
                                        @Nonnull Predicate<E> canceler) {
    super(key,
        eventClass,
        event -> {
          if (canceler.test(event)) {
            event.setCancelled(true);
          }
        });
  }
}