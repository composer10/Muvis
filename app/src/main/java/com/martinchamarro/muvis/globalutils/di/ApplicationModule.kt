/*
 * Copyright 2017 Martin Chamarro (@martinchamarro)
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

package com.martinchamarro.muvis.globalutils.di

import android.app.Application
import android.content.Context
import com.martinchamarro.muvis.globalutils.logger.Logger
import com.martinchamarro.muvis.globalutils.tracker.FirebaseTracker
import com.martinchamarro.muvis.globalutils.tracker.Tracker

import javax.inject.Singleton

import dagger.Module
import dagger.Provides

@Module class ApplicationModule(private val application: Application) {

    @Provides @Singleton internal fun provideApplicationContext(): Context = application

    @Provides @Singleton fun provideLogger() = Logger

    @Provides @Singleton fun provideTracker(context: Context): Tracker = FirebaseTracker(context)

}
