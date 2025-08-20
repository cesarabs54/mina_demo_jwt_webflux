package co.com.bancolombia.usecase.api;


import co.com.bancolombia.usecase.DeleteByIdUseCase;
import co.com.bancolombia.usecase.DeleteUseCase;
import co.com.bancolombia.usecase.GetAllUseCase;
import co.com.bancolombia.usecase.GetByIdUseCase;
import co.com.bancolombia.usecase.SaveUseCase;
import co.com.bancolombia.usecase.UpdateUseCase;
import java.io.Serializable;

public interface GenericServiceAPI<T, I extends Serializable> extends GetByIdUseCase<T, I>,
        SaveUseCase<T>, GetAllUseCase<T>, DeleteUseCase<T>, DeleteByIdUseCase<I>, UpdateUseCase<T> {

}
