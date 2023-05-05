package de.gipmbh.gradle.plugins.release

import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import java.io.File

abstract class VersionConfigExtension {

    @get:Optional
    abstract var version: String

    @get:Input
    @get:Optional
    abstract val leastVersion: Property<String>

    @get:Input
    @get:Optional
    abstract val tagPrefix: Property<String>

    @get:Input
    @get:Optional
    abstract val ignoreUncommittedChanges: Property<Boolean>

    @get:Input
    @get:Optional
    abstract val useHighestVersion: Property<Boolean>

    @get:Input
    @get:Optional
    abstract val incrementer: Property<String>

    @get:Input
    @get:Optional
    abstract val gitDirectory: Property<File>

}