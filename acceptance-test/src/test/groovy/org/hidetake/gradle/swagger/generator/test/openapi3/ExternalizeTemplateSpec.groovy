package org.hidetake.gradle.swagger.generator.test.openapi3


import org.gradle.testkit.runner.TaskOutcome
import org.hidetake.gradle.swagger.generator.test.GradleProject
import spock.lang.Specification

class ExternalizeTemplateSpec extends Specification {

    final project = new GradleProject(':openapi-v3:externalize-template')

    def 'generateSwaggerCode task should generate code using external template'() {
        given:
        project.execute('templates:publishToMavenLocal')

        when:
        def result = project.executeWithoutClean('generateSwaggerCode')

        then:
        result.task(project.absolutePath('resolveSwaggerTemplate')).outcome == TaskOutcome.SUCCESS
        result.task(project.absolutePath('generateSwaggerCode')).outcome == TaskOutcome.NO_SOURCE
        result.task(project.absolutePath('generateSwaggerCodePetstore')).outcome == TaskOutcome.SUCCESS
        project.file('build/swagger-code-petstore/index.html').exists()
    }

}
