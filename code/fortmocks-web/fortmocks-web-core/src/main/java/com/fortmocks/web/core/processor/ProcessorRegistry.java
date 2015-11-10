package com.fortmocks.web.core.processor;

import com.fortmocks.core.model.Input;
import com.fortmocks.core.model.Output;
import com.fortmocks.core.model.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.GenericTypeResolver;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@Component
public class ProcessorRegistry<I extends Input, O extends Output> {

    @Autowired
    private ApplicationContext applicationContext;

    private Map<Class<I>, Processor<I,O>> processors = new HashMap<Class<I>, Processor<I,O>>();

    public Processor<I,O> getProcessor(final I input){
        return processors.get(input.getClass());
    }

    public void initializeProcessorRegistry(){
        final Map<String, Object> components = applicationContext.getBeansWithAnnotation(Service.class);
        for(Map.Entry<String, Object> entry : components.entrySet()){
            final Object value = entry.getValue();
            if(value instanceof Processor){
                final Processor processor = (Processor) value;
                Class<?>[] processorInputOutputClasses = GenericTypeResolver.resolveTypeArguments(processor.getClass(), Processor.class);
                final Class<I> processorInputClass = (Class<I>) processorInputOutputClasses[0];
                processors.put(processorInputClass, processor);
            }
        }
    }

}
