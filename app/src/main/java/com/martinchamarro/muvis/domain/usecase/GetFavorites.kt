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

package com.martinchamarro.muvis.domain.usecase

import com.martinchamarro.muvis.domain.model.Movie
import com.martinchamarro.muvis.domain.repository.MoviesRepository
import com.martinchamarro.muvis.threading.Executor
import com.martinchamarro.muvis.threading.MainThread
import org.funktionale.either.Either
import javax.inject.Inject

class GetFavorites @Inject constructor(
        executor: Executor,
        mainThread: MainThread,
        private val repository: MoviesRepository) : UseCase<List<Movie>>(executor, mainThread) {

    public override fun execute(onSuccess: (List<Movie>) -> Unit, onError: (Throwable) -> Unit) {
        super.execute(onSuccess, onError)
    }

    override fun onExecute(): Either<Throwable, List<Movie>> = repository.getFavorites()

}