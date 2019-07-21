package space.pal.sig.util;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

@Singleton
public class IntelViewModelFactory implements ViewModelProvider.Factory{

    private final Map<Class<? extends ViewModel>, Provider<ViewModel>> creators;
    private ViewModel viewModel;

    @Inject
    public IntelViewModelFactory(Map<Class<? extends ViewModel>, Provider<ViewModel>> creators){
        this.creators=creators;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (viewModel == null) {
            Provider<? extends ViewModel> creator = creators.get(modelClass);
            if (creator == null) {
                for (Map.Entry<Class<? extends ViewModel>, Provider<ViewModel>> entry : creators.entrySet()) {
                    if (modelClass.isAssignableFrom(entry.getKey())) {
                        creator = entry.getValue();
                        break;
                    }
                }
            }
            if (creator == null) {
                throw new IllegalArgumentException("unknown model class " + modelClass);
            }
            try {
                viewModel = (T) creator.get();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return (T) viewModel;
    }
}
