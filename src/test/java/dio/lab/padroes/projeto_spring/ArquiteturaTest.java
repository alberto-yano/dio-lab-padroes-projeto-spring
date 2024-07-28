package dio.lab.padroes.projeto_spring;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.Entity;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;
import static com.tngtech.archunit.library.GeneralCodingRules.*;

@Profile( value = "local" )
@AnalyzeClasses( packages = "dio.lab.padroes.projeto_spring", importOptions = { ImportOption.DoNotIncludeTests.class } )
public class ArquiteturaTest {

  private static final String PACOTE_RAIZ = "dio.lab.padroes.projeto_spring";

  @ArchTest
  private static final ArchRule no_access_to_standard_streams = NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS;

  @ArchTest
  private static final ArchRule no_java_util_logging = NO_CLASSES_SHOULD_USE_JAVA_UTIL_LOGGING;

  @ArchTest
  private static final ArchRule no_generic_exceptions = NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS;

  @ArchTest
  private static final ArchRule no_classes_should_use_jodatime = NO_CLASSES_SHOULD_USE_JODATIME;

  @ArchTest
  private static final ArchRule controllers_devem_residir_no_pacote_controllers = classes().that()
                                                                                           .areAnnotatedWith(
                                                                                             RestController.class )
                                                                                           .should()
                                                                                           .resideInAPackage(
                                                                                             "..controllers.." )
                                                                                           .as(
                                                                                             "Controllers devem estar localizadas no pacote '..controllers'" );

  @ArchTest
  private static final ArchRule services_devem_residir_no_pacote_services = classes().that()
                                                                                     .areAnnotatedWith( Service.class )
                                                                                     .should()
                                                                                     .resideInAPackage( "..services.." )
                                                                                     .as(
                                                                                       "Services devem estar localizadas no pacote '..services'" );

  @ArchTest
  private static final ArchRule controllers_devem_ter_sufixo_controller = classes().that()
                                                                                   .areAnnotatedWith(
                                                                                     RestController.class )
                                                                                   .should()
                                                                                   .haveSimpleNameEndingWith(
                                                                                     "Controller" );

  @ArchTest
  private static final ArchRule services_devem_ter_sufixo_service = classes().that()
                                                                             .areAnnotatedWith( Service.class )
                                                                             .should()
                                                                             .haveSimpleNameEndingWith( "Service" )
                                                                             .orShould()
                                                                             .haveSimpleNameEndingWith( "ServiceImpl" );

  @ArchTest
  private static final ArchRule repositories_devem_ter_sufixo_repository = classes().that()
                                                                                    .resideInAPackage(
                                                                                      "..repositories" )
                                                                                    .should()
                                                                                    .haveSimpleNameEndingWith(
                                                                                      "Repository" );
  @ArchTest
  private static final ArchRule enums_devem_ter_sufixo_enum              = classes().that()
                                                                                    .areEnums()
                                                                                    .should()
                                                                                    .haveSimpleNameEndingWith( "Enum" );

  @ArchTest
  private static final ArchRule enitities_devem_etr_sufixo_entity = classes().that()
                                                                             .areAnnotatedWith( Entity.class )
                                                                             .should()
                                                                             .haveSimpleNameEndingWith( "Entity" );

  @ArchTest
  private static final ArchRule controllers_devem_ser_anotadas_com_arroba_tag = classes().that()
                                                                                         .areAnnotatedWith(
                                                                                           RestController.class )
                                                                                         .should()
                                                                                         .beAnnotatedWith( Tag.class );

  @ArchTest
  private static final ArchRule regras_de_injecoes_entre_camadas = layeredArchitecture().consideringAllDependencies()
                                                                                        .layer( "Controller" )
                                                                                        .definedBy(
                                                                                          PACOTE_RAIZ + ".controllers" )
                                                                                        .layer( "Service" )
                                                                                        .definedBy(
                                                                                          PACOTE_RAIZ + ".services",
                                                                                          PACOTE_RAIZ +
                                                                                          ".services.impl" )
                                                                                        .layer( "Repository" )
                                                                                        .definedBy( PACOTE_RAIZ +
                                                                                                    ".repositories" )
                                                                                        .layer( "Configuration" )
                                                                                        .definedBy(
                                                                                          PACOTE_RAIZ + ".config" )
                                                                                        .whereLayer( "Controller" )
                                                                                        .mayNotBeAccessedByAnyLayer()
                                                                                        .whereLayer( "Service" )
                                                                                        .mayOnlyBeAccessedByLayers(
                                                                                          "Controller", "Service",
                                                                                          "Configuration" )
                                                                                        .whereLayer( "Repository" )
                                                                                        .mayOnlyBeAccessedByLayers(
                                                                                          "Service" );

}
