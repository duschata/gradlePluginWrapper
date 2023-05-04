package de.gipmbh.gradle.plugins.release

import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import java.io.File

abstract class VersionConfigExtension {

    @set:Optional
    abstract var version: String

    @set:Input
    @set:Optional
    abstract var leastVersion: Property<String>

    @set:Input
    @set:Optional
    abstract var tagPrefix: Property<String>

    @set:Input
    @set:Optional
    abstract var ignoreUncommittedChanges: Property<Boolean>

    @set:Input
    @set:Optional
    abstract var useHighestVersion: Property<Boolean>

    @set:Input
    @set:Optional
    abstract var incrementer: Property<String>

    @set:Input
    @set:Optional
    abstract var gitDirectory: Property<File>

    init {
        tagPrefix.convention("V")
    }
}