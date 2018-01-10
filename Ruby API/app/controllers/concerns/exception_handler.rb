<<<<<<< HEAD
module ExceptionHandler
  # provides the more graceful `included` method
  extend ActiveSupport::Concern

  #catches the errors if record not found or record is invalid
  included do
    rescue_from ActiveRecord::RecordNotFound do |e|
      json_response({ message: e.message }, :not_found)
    end

    rescue_from ActiveRecord::RecordInvalid do |e|
      json_response({ message: e.message }, 404)
    end
  end
=======
module ExceptionHandler
  # provides the more graceful `included` method
  extend ActiveSupport::Concern

  #catches the errors if record not found or record is invalid
  included do
    rescue_from ActiveRecord::RecordNotFound do |e|
      json_response({ message: e.message }, :not_found)
    end

    rescue_from ActiveRecord::RecordInvalid do |e|
      json_response({ message: e.message }, 404)
    end
  end
>>>>>>> 56b6fe42c90710e40cc1403a5b27bd420244a2c0
end