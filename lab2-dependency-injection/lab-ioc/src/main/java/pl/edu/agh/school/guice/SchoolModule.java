package pl.edu.agh.school.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import com.google.inject.name.Names;
import pl.edu.agh.logger.FileMessageSerializer;
import pl.edu.agh.logger.IMessageSerializer;
import pl.edu.agh.school.persistence.IPersistenceManager;
import pl.edu.agh.school.persistence.SerializablePersistenceManager;

public class SchoolModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(IPersistenceManager.class).to(SerializablePersistenceManager.class);

        bind(String.class).annotatedWith(Names.named("TeachersStorageFileName"))
                .toInstance("better_teachers.dat");
        bind(String.class).annotatedWith(Names.named("ClassStorageFileName"))
                .toInstance("better_classes.dat");

        bind(IMessageSerializer.class).to(FileMessageSerializer.class);

        bind(String.class).annotatedWith(Names.named("LogFilename"))
                .toInstance("persistence.log");
    }
}
