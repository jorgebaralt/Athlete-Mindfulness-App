class Users::RegistrationsController < Devise::RegistrationsController
  before_action :configure_sign_up_params, only: [:create]
  before_action :configure_account_update_params, only: [:update]
  include Accessible
  include Channels
  # GET /resource/sign_up
  def new
    super
  end

  # POST /resource
  def create
    client = Twilio::REST::Client.new(ENV['account_sid'], ENV['auth_token'])
    service = client.notify.v1.services(ENV['notify_service_sid'])

    @user = User.create!(user_params)
    if params[:coach_id] != nil
      puts "Created a player"
      coach = User.find(@user.coach_id)
      @user.coach_name = coach.first_name+" "+coach.last_name
      @user.is_coach = 0
      @user.save 
      createChannel(params[:email])
    else
      puts "Created a coach"
      @user.is_coach = 1
      @user.save
    end

    # parameters from device
    identity = @user[:id]
    puts @user[:id]
    fcm_token = params[:fcm_token]
    puts fcm_token

    if fcm_token != nil
      puts "Before binding" 
      bind = service.bindings.create(
        identity: identity,
        binding_type: 'fcm',
        address: fcm_token,
      )
      puts bind
      puts "After binding"
    end
    json_response(@user, :created)
  end

  # GET /resource/edit
  # def edit
  #   super
  # end

  # PUT /resource
  # def update
  #   super
  # end

  # DELETE /resource
  # def destroy
  #   super
  # end

  # GET /resource/cancel
  # Forces the session data which is usually expired after sign
  # in to be expired now. This is useful if the user wants to
  # cancel oauth signing in/up in the middle of the process,
  # removing all OAuth session data.
  # def cancel
  #   super
  # end

  # protected

  # If you have extra params to permit, append them to the sanitizer.
  def configure_sign_up_params
    devise_parameter_sanitizer.permit(:sign_up, keys: [:attribute])
  end

  # If you have extra params to permit, append them to the sanitizer.
  def configure_account_update_params
    devise_parameter_sanitizer.permit(:account_update, keys: [:attribute])
  end

  # The path used after sign up.
  def after_sign_up_path_for(resource)
    super(resource)
  end

  # The path used after sign up for inactive accounts.
  def after_inactive_sign_up_path_for(resource)
    super(resource)
  end

  def user_params
    # whitelist params
    params.permit( :first_name, :last_name, :email, :password, :password_confirmation, :phone, :gender, :points, :coach_id, :age, :age_range, :coach_name, :is_coach)
  end
end
