<<<<<<< HEAD
module Response
	# method used for easy json responses 
  def json_response(object, status = :ok)
    render json: object, status: status
  end
=======
module Response
	# method used for easy json responses 
  def json_response(object, status = :ok)
    render json: object, status: status
  end
>>>>>>> 56b6fe42c90710e40cc1403a5b27bd420244a2c0
end