class Users::SessionsController < Devise::SessionsController
  # before_action :configure_sign_in_params, only: [:create]
  include Accessible
  before_action :check_user, :except=>[:new, :create, :destroy]
  skip_before_action :verify_signed_out_user, only: :destroy
  # before_action :authenticate_user!, only: [:update, :destroy]

  # GET /resource/sign_in
  def new
    super
  end

  # POST /resource/sign_in
  def create
    user_password = params[:session][:password]
    user_email = params[:session][:email]
    user = user_email.present? && User.find_by(email: user_email)

    if user
      if user.valid_password? user_password
        sign_in user, store: false
        user.generate_authentication_token!
        user.save
        render json: user, status:200
      else
        render json: { message: "Invalid email or password" }, status: 422
      end
    else
      render json: { message:"Invalid email or password" }, status: 422
    end
  end

  # POST /resource/sign_out
  def destroy
    user = User.find_by(auth_token: params[:auth_token])
    user.auth_token = nil
    user.save
    json_response(user)
  end

  # protected

  # If you have extra params to permit, append them to the sanitizer.
  def configure_sign_in_params
    devise_parameter_sanitizer.permit(:sign_in, keys: [:attribute])
  end
end
