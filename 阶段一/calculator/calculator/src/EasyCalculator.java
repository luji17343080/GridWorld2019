import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.regex.Pattern;

final public class EasyCalculator {
    public EasyCalculator(){
        
    }
    /* 初始化变量 */
    static private double num1 = 12;
    static private double num2 = 2;
    static private double result = 0;
    /* 设置文本、Label和Button */
    static private JTextField num1Text = new JTextField(Double.toString(num1));
    static private JLabel operatorLabel = new JLabel("");
    static private JTextField num2Text = new JTextField(Double.toString(num2));
    static private JLabel equalLabel = new JLabel("=");
    static private JLabel resultLabel = new JLabel("");
    static private JButton add = new JButton("+");
    static private JButton sub = new JButton("-");
    static private JButton mul = new JButton("*");
    static private JButton div = new JButton("/");
    static private JButton eq = new JButton("OK");

    /* 运算符事件：将运算符添加到运算框内并将结果框清空 */
    static class OperatorAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            resultLabel.setText("");
            operatorLabel.setText(command);
        }
    }
    /* 计算事件：得出计算结果并将结果添加到结果框内  */ 
    static class CalculateAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Pattern p = Pattern.compile("-?(0|[1-9])[0-9]*.?[0-9]*"); //定义数字的正则表达式
            Boolean flag1 = p.matcher(num1Text.getText()).matches();
            Boolean flag2 = p.matcher(num2Text.getText()).matches();
            /* 判别左右操作数是否为数字 */
            if (!flag1 || !flag2) {
                result = 0.0/0.0;
            }
            else {
                num1 = Double.parseDouble(num1Text.getText());
                num2 = Double.parseDouble(num2Text.getText());
                String command = operatorLabel.getText();
                /*此处的if判等不能用"=="，只能调用equals函数*/
                if (command.equals("+")) {
                    result = num1 + num2;
                } else if (command.equals("-")) {
                    result = num1 - num2;
                } else if (command.equals("*")) {
                    result = num1 * num2;
                } else if (command.equals("/")) {
                    result = num1 / num2;
                } 
            }
            /* 将结果写到结果框，注意要转换为string */
            resultLabel.setText(Double.toString(result));
        }
    }
    /* UI */
    static public void main(String[] args) {
        JFrame frame = new JFrame("EasyCalculator");
        frame.setSize(400, 200); //设置窗口大小
        frame.setLayout(new GridLayout(0, 5)); //Grid布局
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true); //设置为可见
        /* 为框架添加元素框，注意按顺序 */
        frame.add(num1Text);
        frame.add(operatorLabel);
        frame.add(num2Text);
        frame.add(equalLabel);
        frame.add(resultLabel);
        frame.add(add);
        frame.add(sub);
        frame.add(mul);
        frame.add(div);
        frame.add(eq);

        /* 框内内容居中 */
        num1Text.setHorizontalAlignment(SwingConstants.CENTER);
        num2Text.setHorizontalAlignment(SwingConstants.CENTER);
        operatorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        equalLabel.setHorizontalAlignment(SwingConstants.CENTER);
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        /* 设置框的边界 */
        num1Text.setBorder(BorderFactory.createLineBorder(Color.gray));
        num2Text.setBorder(BorderFactory.createLineBorder(Color.gray));
        operatorLabel.setBorder(BorderFactory.createLineBorder(Color.gray));
        equalLabel.setBorder(BorderFactory.createLineBorder(Color.gray));
        resultLabel.setBorder(BorderFactory.createLineBorder(Color.gray));

        /* 为各Button添加事件 */
        /* 运算符事件：将运算符添加到运算框内 */
        ActionListener command = new OperatorAction();
        add.addActionListener(new OperatorAction());
        sub.addActionListener(new OperatorAction());
        mul.addActionListener(new OperatorAction());
        div.addActionListener(new OperatorAction());
        /* 计算：得出计算结果 */
        eq.addActionListener(new CalculateAction());
        
    }
}
