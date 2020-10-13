import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MovieSearchResponse (
	@SerializedName("Search") val search : List<Search>,
	@SerializedName("totalResults") val totalResults : Int,
	@SerializedName("Response") val response : String
) :Serializable