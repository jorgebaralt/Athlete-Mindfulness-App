module Response
	# method used for easy json responses 
  def json_response(object, status = :ok)
    render json: object, status: status
  end
end