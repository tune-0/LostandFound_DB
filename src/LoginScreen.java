import javax.swing.*;
import java.awt.*;

public class LoginScreen extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin, btnCancel;
    private JCheckBox chkShowPassword;
    private UserDAO userDAO;
    private User loggedInUser = null;

    public LoginScreen() {
        userDAO = new UserDAO();
        initComponents();
    }

    private void initComponents() {
        setTitle("Login - Lost and Found System");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        // Main container
        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 0, 0));

        // ========== LEFT SIDE - Image/Illustration Panel ==========
        JPanel leftPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

                // Modern gradient background
                int w = getWidth();
                int h = getHeight();
                Color color1 = new Color(106, 17, 203); // Purple
                Color color2 = new Color(37, 117, 252); // Blue
                GradientPaint gp = new GradientPaint(0, 0, color1, w, h, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);

                // Add decorative circles
                g2d.setColor(new Color(255, 255, 255, 30));
                g2d.fillOval(-50, -50, 300, 300);
                g2d.fillOval(w - 250, h - 250, 300, 300);

                g2d.setColor(new Color(255, 255, 255, 20));
                g2d.fillOval(w - 150, 50, 200, 200);
            }
        };
        leftPanel.setLayout(new GridBagLayout());

        GridBagConstraints leftGbc = new GridBagConstraints();
        leftGbc.gridx = 0;
        leftGbc.insets = new Insets(20, 40, 20, 40);

        // Main title
        leftGbc.gridy = 1;
        leftGbc.insets = new Insets(30, 40, 10, 40);
        JLabel lblMainTitle = new JLabel("<html><div style='text-align: center; color: white;'><h1 style='font-size: 36px; margin: 0;'>Lost & Found</h1></div></html>");
        lblMainTitle.setForeground(Color.WHITE);
        leftPanel.add(lblMainTitle, leftGbc);

        leftGbc.gridy = 2;
        leftGbc.insets = new Insets(0, 40, 30, 40);
        JLabel lblMainSubtitle = new JLabel("<html><div style='text-align: center; color: white;'><h2 style='font-size: 28px; margin: 0;'>Management System</h2></div></html>");
        lblMainSubtitle.setForeground(new Color(255, 255, 255, 230));
        leftPanel.add(lblMainSubtitle, leftGbc);

        // Description
        leftGbc.gridy = 3;
        leftGbc.insets = new Insets(10, 60, 20, 60);
        JLabel lblDescription = new JLabel("<html><div style='text-align: center; color: white; width: 320px;'>Efficiently track and manage found and lost items with our modern, secure system.</div></html>");
        lblDescription.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblDescription.setForeground(new Color(255, 255, 255, 200));
        leftPanel.add(lblDescription, leftGbc);

        // Features list
        leftGbc.gridy = 4;
        leftGbc.insets = new Insets(30, 60, 20, 60);
        JPanel featuresPanel = new JPanel();
        featuresPanel.setLayout(new BoxLayout(featuresPanel, BoxLayout.Y_AXIS));
        featuresPanel.setOpaque(false);

        String[] features = {"✓ Real-time tracking", "✓ Secure archive system", "✓ Advanced search & filters"};
        for (String feature : features) {
            JLabel lblFeature = new JLabel(feature);
            lblFeature.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            lblFeature.setForeground(Color.WHITE);
            lblFeature.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
            featuresPanel.add(lblFeature);
        }
        leftPanel.add(featuresPanel, leftGbc);

        // ========== RIGHT SIDE - Login Form Panel ==========
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 60, 10, 60);

        // Welcome back
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(60, 60, 10, 60);
        JLabel lblWelcome = new JLabel("Welcome back");
        lblWelcome.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblWelcome.setForeground(new Color(30, 30, 30));
        rightPanel.add(lblWelcome, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 60, 40, 60);
        JLabel lblWelcomeSub = new JLabel("Sign in to your account");
        lblWelcomeSub.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblWelcomeSub.setForeground(new Color(120, 120, 120));
        rightPanel.add(lblWelcomeSub, gbc);

        // Username
        gbc.gridy = 2;
        gbc.insets = new Insets(20, 60, 8, 60);
        JLabel lblUsername = new JLabel("Username");
        lblUsername.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblUsername.setForeground(new Color(60, 60, 60));
        rightPanel.add(lblUsername, gbc);

        gbc.gridy = 3;
        gbc.insets = new Insets(0, 60, 20, 60);
        txtUsername = new JTextField();
        txtUsername.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txtUsername.setPreferredSize(new Dimension(340, 50));
        txtUsername.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        // Focus effect
        txtUsername.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtUsername.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(40, 89, 181), 2),
                        BorderFactory.createEmptyBorder(8, 15, 8, 15)
                ));
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtUsername.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                        BorderFactory.createEmptyBorder(8, 15, 8, 15)
                ));
            }
        });
        rightPanel.add(txtUsername, gbc);

        // Password
        gbc.gridy = 4;
        gbc.insets = new Insets(10, 60, 8, 60);
        JLabel lblPassword = new JLabel("Password");
        lblPassword.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblPassword.setForeground(new Color(60, 60, 60));
        rightPanel.add(lblPassword, gbc);

        gbc.gridy = 5;
        gbc.insets = new Insets(0, 60, 15, 60);
        txtPassword = new JPasswordField();
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txtPassword.setPreferredSize(new Dimension(340, 50));
        txtPassword.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        // Focus effect
        txtPassword.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPassword.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(40, 89, 181), 2),
                        BorderFactory.createEmptyBorder(8, 15, 8, 15)
                ));
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPassword.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                        BorderFactory.createEmptyBorder(8, 15, 8, 15)
                ));
            }
        });
        rightPanel.add(txtPassword, gbc);

        // Show password
        gbc.gridy = 6;
        gbc.insets = new Insets(0, 60, 25, 60);
        chkShowPassword = new JCheckBox("Show password");
        chkShowPassword.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        chkShowPassword.setBackground(Color.WHITE);
        chkShowPassword.setForeground(new Color(100, 100, 100));
        chkShowPassword.setFocusPainted(false);
        rightPanel.add(chkShowPassword, gbc);

        // Login button
        gbc.gridy = 7;
        gbc.insets = new Insets(10, 60, 12, 60);
        btnLogin = new JButton("Log In");
        btnLogin.setPreferredSize(new Dimension(340, 55));
        btnLogin.setBackground(new Color(40, 89, 181));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnLogin.setFocusPainted(false);
        btnLogin.setBorderPainted(false);
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        rightPanel.add(btnLogin, gbc);

        // Cancel button
        gbc.gridy = 8;
        gbc.insets = new Insets(0, 60, 30, 60);
        btnCancel = new JButton("Cancel");
        btnCancel.setPreferredSize(new Dimension(340, 55));
        btnCancel.setBackground(new Color(245, 245, 245));
        btnCancel.setForeground(new Color(80, 80, 80));
        btnCancel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnCancel.setFocusPainted(false);
        btnCancel.setBorderPainted(false);
        btnCancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        rightPanel.add(btnCancel, gbc);

        // Default credentials
        gbc.gridy = 9;
        gbc.insets = new Insets(20, 60, 40, 60);
        JPanel credPanel = new JPanel();
        credPanel.setBackground(new Color(250, 245, 255));
        credPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(106, 17, 203, 50), 1),
                BorderFactory.createEmptyBorder(12, 20, 12, 20)
        ));
        JLabel lblCred = new JLabel("<html><center>💡 <b>Test Credentials:</b> admin / admin123</center></html>");
        lblCred.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblCred.setForeground(new Color(80, 80, 80));
        credPanel.add(lblCred);
        rightPanel.add(credPanel, gbc);

        // Add panels to main
        mainPanel.add(leftPanel);
        mainPanel.add(rightPanel);
        add(mainPanel);

        // Action Listeners
        addActionListeners();

        // Hover effects
        addHoverEffect(btnLogin, new Color(40, 89, 181), new Color(48, 103, 211));
        addHoverEffect(btnCancel, new Color(245, 245, 245), new Color(207, 207, 207));
    }

    private void addHoverEffect(JButton button, Color normalColor, Color hoverColor) {
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hoverColor);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(normalColor);
            }
        });
    }

    private void addActionListeners() {
        chkShowPassword.addActionListener(e -> {
            if (chkShowPassword.isSelected()) {
                txtPassword.setEchoChar((char) 0);
            } else {
                txtPassword.setEchoChar('•');
            }
        });

        btnLogin.addActionListener(e -> performLogin());

        btnCancel.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to exit?",
                    "Confirm Exit",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        txtPassword.addActionListener(e -> performLogin());
        txtUsername.addActionListener(e -> txtPassword.requestFocus());
    }

    private void performLogin() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (username.isEmpty()) {
            showError("Please enter your username!");
            txtUsername.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            showError("Please enter your password!");
            txtPassword.requestFocus();
            return;
        }

        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        btnLogin.setEnabled(false);
        btnLogin.setText("Signing in...");

        Timer timer = new Timer(500, e -> {
            loggedInUser = userDAO.authenticateUser(username, password);

            setCursor(Cursor.getDefaultCursor());
            btnLogin.setEnabled(true);
            btnLogin.setText("Sign In");

            if (loggedInUser != null) {
                this.dispose();
                SwingUtilities.invokeLater(() -> {
                    LostAndFoundGUI mainApp = new LostAndFoundGUI(loggedInUser);
                    mainApp.setVisible(true);
                });
            } else {
                showError("Invalid username or password!");
                txtPassword.setText("");
                txtUsername.requestFocus();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this,
                message,
                "Login Failed",
                JOptionPane.ERROR_MESSAGE);
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            LoginScreen loginScreen = new LoginScreen();
            loginScreen.setVisible(true);
        });
    }
}